package com.ebanking.transaction.dto.response;

import com.ebanking.transaction.enums.TransactionStatus;
import com.ebanking.transaction.enums.TransactionType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String reference;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private Long sourceAccountId;
    private Long targetAccountId;
    private String description;
    private String rejectionReason;
    private LocalDateTime createdAt;
}
