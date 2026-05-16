package com.ebanking.account.mapper;

import com.ebanking.account.dto.response.AccountResponse;
import com.ebanking.account.dto.response.BalanceResponse;
import com.ebanking.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toResponse(Account account);

    @Mapping(target = "accountId", source = "id")
    BalanceResponse toBalanceResponse(Account account);
}
