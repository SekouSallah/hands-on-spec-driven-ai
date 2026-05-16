package com.ebanking.card.entity;

import com.ebanking.card.enums.CardStatus;
import com.ebanking.card.enums.CardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "cards") @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Card {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pan", nullable = false, unique = true, length = 16)
    private String pan;
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    @Column(name = "cvv", nullable = false, length = 60)
    private String cvv;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false) @Builder.Default
    private CardStatus status = CardStatus.ACTIVE;
    @Enumerated(EnumType.STRING) @Column(name = "card_type", nullable = false)
    private CardType cardType;
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    @CreationTimestamp @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
