package com.ebanking.account.repository;

import com.ebanking.account.entity.Account;
import com.ebanking.account.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByCustomerId(Long customerId);

    Page<Account> findByCustomerId(Long customerId, Pageable pageable);

    Page<Account> findByStatus(AccountStatus status, Pageable pageable);

    long countByStatus(AccountStatus status);

    List<Account> findByCustomerIdAndStatus(Long customerId, AccountStatus status);
}
