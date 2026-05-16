package com.ebanking.account.dto.response;

import com.ebanking.account.enums.AccountStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
