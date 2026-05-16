package com.ebanking.account.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private Long accountId;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
}
