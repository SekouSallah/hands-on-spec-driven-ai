package com.ebanking.notification.dto.response;

import com.ebanking.notification.enums.NotificationStatus;
import com.ebanking.notification.enums.NotificationType;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String recipient;
    private String subject;
    private String content;
    private NotificationType type;
    private NotificationStatus status;
    private Long customerId;
    private String errorMessage;
    private LocalDateTime createdAt;
}
