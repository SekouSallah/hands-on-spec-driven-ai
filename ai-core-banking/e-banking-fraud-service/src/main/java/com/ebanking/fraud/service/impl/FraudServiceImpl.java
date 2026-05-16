package com.ebanking.fraud.service.impl;

import com.ebanking.fraud.dto.response.FraudAlertResponse;
import com.ebanking.fraud.entity.FraudAlert;
import com.ebanking.fraud.enums.FraudLevel;
import com.ebanking.fraud.exception.BusinessRuleException;
import com.ebanking.fraud.exception.ResourceNotFoundException;
import com.ebanking.fraud.mapper.FraudAlertMapper;
import com.ebanking.fraud.repository.FraudAlertRepository;
import com.ebanking.fraud.service.FraudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudServiceImpl implements FraudService {

    private final FraudAlertRepository fraudAlertRepository;
    private final FraudAlertMapper fraudAlertMapper;
    private final RestTemplate restTemplate;

    @Value("${ebanking.services.transaction-url:http://localhost:8084}")
    private String transactionServiceUrl;

    @Override
    @Transactional
    public FraudAlertResponse resolveAlert(Long id) {
        FraudAlert alert = findAlertById(id);
        if (alert.getResolved()) {
            throw new BusinessRuleException("ALERT_ALREADY_RESOLVED", "This alert is already resolved");
        }
        alert.setResolved(true);
        log.info("Fraud alert {} resolved", id);
        return fraudAlertMapper.toResponse(fraudAlertRepository.save(alert));
    }

    @Override
    @Transactional(readOnly = true)
    public FraudAlertResponse findById(Long id) {
        return fraudAlertMapper.toResponse(findAlertById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FraudAlertResponse> findAll(Pageable pageable) {
        return fraudAlertRepository.findAll(pageable).map(fraudAlertMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FraudAlertResponse> findByLevel(FraudLevel level, Pageable pageable) {
        return fraudAlertRepository.findByLevel(level, pageable).map(fraudAlertMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FraudAlertResponse> findByResolved(Boolean resolved, Pageable pageable) {
        return fraudAlertRepository.findByResolved(resolved, pageable).map(fraudAlertMapper::toResponse);
    }

    /**
     * Batch job running every hour to analyze transactions
     */
    @Override
    @Transactional
    @Scheduled(cron = "${ebanking.fraud.batch.cron:0 0 * * * *}")
    public void analyzeRecentTransactions() {
        log.info("Starting fraud analysis batch job");
        
        try {
            // In a real scenario, this would fetch only unchecked transactions.
            // For workshop: fetch all from transaction-service and run basic rules.
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    transactionServiceUrl + "/api/transactions?size=100",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getBody() != null && response.getBody().containsKey("content")) {
                List<Map<String, Object>> transactions = (List<Map<String, Object>>) response.getBody().get("content");
                
                for (Map<String, Object> tx : transactions) {
                    analyzeTransaction(tx);
                }
            }
        } catch (Exception e) {
            log.error("Error during fraud analysis batch: {}", e.getMessage());
        }
    }

    private void analyzeTransaction(Map<String, Object> tx) {
        Long txId = ((Number) tx.get("id")).longValue();
        Double amountVal = ((Number) tx.get("amount")).doubleValue();
        BigDecimal amount = BigDecimal.valueOf(amountVal);
        String status = (String) tx.get("status");
        
        // Very basic rule: Amount > 5000 is HIGH risk, > 1000 is MEDIUM risk
        if ("COMPLETED".equals(status) || "PENDING".equals(status)) {
            if (amount.compareTo(new BigDecimal("5000")) > 0) {
                createAlertIfNotExists(txId, FraudLevel.HIGH, 85, "Large transaction amount exceeding 5000");
            } else if (amount.compareTo(new BigDecimal("1000")) > 0) {
                createAlertIfNotExists(txId, FraudLevel.MEDIUM, 50, "Transaction amount exceeding 1000");
            }
        }
    }

    private void createAlertIfNotExists(Long txId, FraudLevel level, int score, String reason) {
        // Assume we only want to alert once per transaction (in a real system, we'd check existence properly)
        // For workshop simplicity, we just create it.
        FraudAlert alert = FraudAlert.builder()
                .transactionId(txId)
                .level(level)
                .score(score)
                .reason(reason)
                .resolved(false)
                .build();
        
        fraudAlertRepository.save(alert);
        log.warn("Created Fraud Alert: Level {}, Score {}, TX {}", level, score, txId);
    }

    private FraudAlert findAlertById(Long id) {
        return fraudAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FraudAlert", "id", id));
    }
}
