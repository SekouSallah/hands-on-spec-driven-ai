package com.ebanking.transaction.controller;

import com.ebanking.transaction.dto.request.TransferRequest;
import com.ebanking.transaction.dto.response.TransactionResponse;
import com.ebanking.transaction.service.TransactionService;
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

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "Operations for financial transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    @Operation(summary = "Create an internal transfer")
    public ResponseEntity<TransactionResponse> createTransfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransfer(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Get all transactions with pagination")
    public ResponseEntity<Page<TransactionResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(transactionService.findAll(pageable));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transaction history for an account")
    public ResponseEntity<Page<TransactionResponse>> findByAccount(
            @PathVariable Long accountId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(transactionService.findByAccountId(accountId, pageable));
    }

    @PatchMapping("/{id}/validate")
    @Operation(summary = "Validate a pending transaction")
    public ResponseEntity<TransactionResponse> validate(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.validateTransaction(id));
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Reject a pending transaction")
    public ResponseEntity<TransactionResponse> reject(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(transactionService.rejectTransaction(id, reason));
    }
}
