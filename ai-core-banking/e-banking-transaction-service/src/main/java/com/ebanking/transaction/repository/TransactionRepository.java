package com.ebanking.transaction.repository;

import com.ebanking.transaction.entity.Transaction;
import com.ebanking.transaction.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByReference(String reference);

    Page<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId, Pageable pageable);

    Page<Transaction> findByStatus(TransactionStatus status, Pageable pageable);

    long countByStatus(TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE t.createdAt >= :since")
    List<Transaction> findRecentTransactions(@Param("since") LocalDateTime since);

    @Query("SELECT t FROM Transaction t WHERE t.sourceAccountId = :accountId AND t.createdAt >= :since")
    List<Transaction> findBySourceAccountIdAndCreatedAtAfter(
            @Param("accountId") Long accountId, @Param("since") LocalDateTime since);
}
