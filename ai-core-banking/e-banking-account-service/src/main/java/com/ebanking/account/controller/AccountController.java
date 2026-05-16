package com.ebanking.account.controller;

import com.ebanking.account.dto.request.CreateAccountRequest;
import com.ebanking.account.dto.response.AccountResponse;
import com.ebanking.account.dto.response.BalanceResponse;
import com.ebanking.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management", description = "Operations for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Create a new bank account")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping("/{id}/balance")
    @Operation(summary = "Get account balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getBalance(id));
    }

    @GetMapping
    @Operation(summary = "Get all accounts with pagination")
    public ResponseEntity<Page<AccountResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(accountService.findAll(pageable));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get accounts by customer ID")
    public ResponseEntity<List<AccountResponse>> findByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.findByCustomerId(customerId));
    }

    @PatchMapping("/{id}/close")
    @Operation(summary = "Close an account (balance must be zero)")
    public ResponseEntity<AccountResponse> closeAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.closeAccount(id));
    }

    @PatchMapping("/{id}/freeze")
    @Operation(summary = "Freeze an account")
    public ResponseEntity<AccountResponse> freezeAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.freezeAccount(id));
    }

    @PatchMapping("/{id}/unfreeze")
    @Operation(summary = "Unfreeze an account")
    public ResponseEntity<AccountResponse> unfreezeAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.unfreezeAccount(id));
    }

    @PatchMapping("/{id}/balance")
    @Operation(summary = "Update account balance (internal use by transaction-service)")
    public ResponseEntity<AccountResponse> updateBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountService.updateBalance(id, amount));
    }
}
