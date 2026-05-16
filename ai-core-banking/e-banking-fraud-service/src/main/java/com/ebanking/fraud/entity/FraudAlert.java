package com.ebanking.fraud.entity;

import com.ebanking.fraud.enums.FraudLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "fraud_alerts") @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FraudAlert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) @Column(name = "level", nullable = false, length = 50)
    private FraudLevel level;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "resolved", nullable = false) @Builder.Default
    private Boolean resolved = false;

    @CreationTimestamp @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
