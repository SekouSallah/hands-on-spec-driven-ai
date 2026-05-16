package com.ebanking.transaction.service.impl;

import com.ebanking.transaction.dto.request.TransferRequest;
import com.ebanking.transaction.dto.response.TransactionResponse;
import com.ebanking.transaction.entity.Transaction;
import com.ebanking.transaction.enums.TransactionStatus;
import com.ebanking.transaction.enums.TransactionType;
import com.ebanking.transaction.exception.BusinessRuleException;
import com.ebanking.transaction.exception.ResourceNotFoundException;
import com.ebanking.transaction.mapper.TransactionMapper;
import com.ebanking.transaction.repository.TransactionRepository;
import com.ebanking.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final RestTemplate restTemplate;

    @Value("${ebanking.services.account-url:http://localhost:8082}")
    private String accountServiceUrl;

    @Override
    @Transactional
    public TransactionResponse createTransfer(TransferRequest request) {
        log.info("Creating transfer: {} -> {}, amount: {}", 
                request.getSourceAccountId(), request.getTargetAccountId(), request.getAmount());

        if (request.getSourceAccountId().equals(request.getTargetAccountId())) {
            throw new BusinessRuleException("TRANSACTION_INVALID",
                    "Source and target accounts must be different");
        }

        String reference = generateReference();

        Transaction transaction = Transaction.builder()
                .reference(reference)
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .sourceAccountId(request.getSourceAccountId())
                .targetAccountId(request.getTargetAccountId())
                .description(request.getDescription())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        // Try to execute the transfer via account-service
        try {
            // Debit source account
            restTemplate.patchForObject(
                    accountServiceUrl + "/api/accounts/{id}/balance?amount={amount}",
                    null, Object.class,
                    request.getSourceAccountId(),
                    request.getAmount().negate());

            // Credit target account
            restTemplate.patchForObject(
                    accountServiceUrl + "/api/accounts/{id}/balance?amount={amount}",
                    null, Object.class,
                    request.getTargetAccountId(),
                    request.getAmount());

            saved.setStatus(TransactionStatus.COMPLETED);
            log.info("Transfer completed: {}", reference);
        } catch (Exception e) {
            log.error("Transfer failed: {} — {}", reference, e.getMessage());
            saved.setStatus(TransactionStatus.FAILED);
            saved.setRejectionReason("Transfer execution failed: " + e.getMessage());
        }

        return transactionMapper.toResponse(transactionRepository.save(saved));
    }

    @Override
    @Transactional
    public TransactionResponse validateTransaction(Long id) {
        Transaction transaction = findTransactionById(id);
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new BusinessRuleException("TRANSACTION_ALREADY_PROCESSED",
                    "Transaction is already " + transaction.getStatus());
        }
        transaction.setStatus(TransactionStatus.COMPLETED);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    @Override
    @Transactional
    public TransactionResponse rejectTransaction(Long id, String reason) {
        Transaction transaction = findTransactionById(id);
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new BusinessRuleException("TRANSACTION_ALREADY_PROCESSED",
                    "Transaction is already " + transaction.getStatus());
        }
        if (reason == null || reason.isBlank()) {
            throw new BusinessRuleException("TRANSACTION_INVALID",
                    "Rejection reason is required");
        }
        transaction.setStatus(TransactionStatus.REJECTED);
        transaction.setRejectionReason(reason);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse findById(Long id) {
        return transactionMapper.toResponse(findTransactionById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> findByAccountId(Long accountId, Pageable pageable) {
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId, pageable)
                .map(transactionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(transactionMapper::toResponse);
    }

    private String generateReference() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uid = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return "TXN-" + date + "-" + uid;
    }

    private Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));
    }
}
