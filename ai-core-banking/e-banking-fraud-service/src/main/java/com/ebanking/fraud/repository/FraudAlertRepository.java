package com.ebanking.fraud.repository;

import com.ebanking.fraud.entity.FraudAlert;
import com.ebanking.fraud.enums.FraudLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {
    Page<FraudAlert> findByLevel(FraudLevel level, Pageable pageable);
    Page<FraudAlert> findByResolved(Boolean resolved, Pageable pageable);
    Page<FraudAlert> findByLevelAndResolved(FraudLevel level, Boolean resolved, Pageable pageable);
    long countByLevel(FraudLevel level);
    long countByResolved(Boolean resolved);
}
