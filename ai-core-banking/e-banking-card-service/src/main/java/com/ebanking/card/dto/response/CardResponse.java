package com.ebanking.card.dto.response;

import com.ebanking.card.enums.CardStatus;
import com.ebanking.card.enums.CardType;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CardResponse {
    private Long id;
    private String maskedPan; // **** **** **** 1234
    private LocalDate expiryDate;
    private CardStatus status;
    private CardType cardType;
    private Long accountId;
    private LocalDateTime createdAt;
}
