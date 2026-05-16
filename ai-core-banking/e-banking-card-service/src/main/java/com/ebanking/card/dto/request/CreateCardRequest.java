package com.ebanking.card.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CreateCardRequest {
    @NotNull(message = "Account ID is required")
    private Long accountId;
    private String cardType; // DEBIT or CREDIT, defaults to DEBIT
}
