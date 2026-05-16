package com.ebanking.transaction.mapper;

import com.ebanking.transaction.dto.response.TransactionResponse;
import com.ebanking.transaction.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
