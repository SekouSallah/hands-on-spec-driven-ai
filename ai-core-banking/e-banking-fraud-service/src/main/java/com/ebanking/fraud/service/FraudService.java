package com.ebanking.fraud.service;

import com.ebanking.fraud.dto.response.FraudAlertResponse;
import com.ebanking.fraud.enums.FraudLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FraudService {
    FraudAlertResponse resolveAlert(Long id);
    FraudAlertResponse findById(Long id);
    Page<FraudAlertResponse> findAll(Pageable pageable);
    Page<FraudAlertResponse> findByLevel(FraudLevel level, Pageable pageable);
    Page<FraudAlertResponse> findByResolved(Boolean resolved, Pageable pageable);
    
    // Triggered manually or by scheduler
    void analyzeRecentTransactions();
}
