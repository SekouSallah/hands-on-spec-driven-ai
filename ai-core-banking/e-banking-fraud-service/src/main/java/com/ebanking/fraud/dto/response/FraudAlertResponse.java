package com.ebanking.fraud.dto.response;

import com.ebanking.fraud.enums.FraudLevel;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FraudAlertResponse {
    private Long id;
    private FraudLevel level;
    private Integer score;
    private String reason;
    private Long transactionId;
    private Boolean resolved;
    private LocalDateTime createdAt;
}
