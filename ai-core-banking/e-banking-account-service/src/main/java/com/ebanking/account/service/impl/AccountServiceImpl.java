package com.ebanking.account.service.impl;

import com.ebanking.account.dto.request.CreateAccountRequest;
import com.ebanking.account.dto.response.AccountResponse;
import com.ebanking.account.dto.response.BalanceResponse;
import com.ebanking.account.entity.Account;
import com.ebanking.account.enums.AccountStatus;
import com.ebanking.account.exception.BusinessRuleException;
import com.ebanking.account.exception.ResourceNotFoundException;
import com.ebanking.account.mapper.AccountMapper;
import com.ebanking.account.repository.AccountRepository;
import com.ebanking.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        log.info("Creating account for customer: {}", request.getCustomerId());

        String accountNumber = generateAccountNumber();
        String currency = request.getCurrency() != null ? request.getCurrency() : "EUR";

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .currency(currency)
                .status(AccountStatus.ACTIVE)
                .customerId(request.getCustomerId())
                .build();

        Account saved = accountRepository.save(account);
        log.info("Account created: {} for customer: {}", saved.getAccountNumber(), saved.getCustomerId());
        return accountMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AccountResponse closeAccount(Long id) {
        log.info("Closing account: {}", id);
        Account account = findAccountById(id);

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new BusinessRuleException("ACCOUNT_CLOSED", "Account is already closed");
        }
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleException("ACCOUNT_BALANCE_NOT_ZERO",
                    "Account balance must be zero to close. Current balance: " + account.getBalance());
        }

        account.setStatus(AccountStatus.CLOSED);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponse freezeAccount(Long id) {
        log.info("Freezing account: {}", id);
        Account account = findAccountById(id);

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new BusinessRuleException("ACCOUNT_NOT_ACTIVE",
                    "Can only freeze ACTIVE accounts. Current status: " + account.getStatus());
        }

        account.setStatus(AccountStatus.FROZEN);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponse unfreezeAccount(Long id) {
        log.info("Unfreezing account: {}", id);
        Account account = findAccountById(id);

        if (account.getStatus() != AccountStatus.FROZEN) {
            throw new BusinessRuleException("ACCOUNT_NOT_FROZEN",
                    "Can only unfreeze FROZEN accounts. Current status: " + account.getStatus());
        }

        account.setStatus(AccountStatus.ACTIVE);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) {
        return accountMapper.toResponse(findAccountById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long id) {
        Account account = findAccountById(id);
        return accountMapper.toBalanceResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponse> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toResponse);
    }

    @Override
    @Transactional
    public AccountResponse updateBalance(Long id, BigDecimal amount) {
        log.info("Updating balance for account {}: {}", id, amount);
        Account account = findAccountById(id);

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new BusinessRuleException("ACCOUNT_NOT_ACTIVE",
                    "Cannot update balance of non-active account. Status: " + account.getStatus());
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleException("INSUFFICIENT_BALANCE",
                    "Insufficient balance. Available: " + account.getBalance() + ", requested: " + amount.abs());
        }

        account.setBalance(newBalance);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    /**
     * Generate a unique account number in format EB-XXXX-XXXX-XXXX.
     */
    private String generateAccountNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        return String.format("EB-%s-%s-%s", uuid.substring(0, 4), uuid.substring(4, 8), uuid.substring(8, 12));
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }
}
