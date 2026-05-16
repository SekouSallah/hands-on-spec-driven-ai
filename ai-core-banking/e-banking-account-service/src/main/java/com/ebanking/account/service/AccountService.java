package com.ebanking.account.service;

import com.ebanking.account.dto.request.CreateAccountRequest;
import com.ebanking.account.dto.response.AccountResponse;
import com.ebanking.account.dto.response.BalanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse closeAccount(Long id);

    AccountResponse freezeAccount(Long id);

    AccountResponse unfreezeAccount(Long id);

    AccountResponse findById(Long id);

    BalanceResponse getBalance(Long id);

    List<AccountResponse> findByCustomerId(Long customerId);

    Page<AccountResponse> findAll(Pageable pageable);

    /**
     * Update account balance (called by transaction-service).
     */
    AccountResponse updateBalance(Long id, BigDecimal amount);
}
