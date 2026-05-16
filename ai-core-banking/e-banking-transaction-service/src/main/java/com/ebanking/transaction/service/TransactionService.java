package com.ebanking.transaction.service;

import com.ebanking.transaction.dto.request.TransferRequest;
import com.ebanking.transaction.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    TransactionResponse createTransfer(TransferRequest request);
    TransactionResponse validateTransaction(Long id);
    TransactionResponse rejectTransaction(Long id, String reason);
    TransactionResponse findById(Long id);
    Page<TransactionResponse> findByAccountId(Long accountId, Pageable pageable);
    Page<TransactionResponse> findAll(Pageable pageable);
}
