package com.ebanking.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SendNotificationRequest {
    @NotBlank(message = "Recipient is required")
    private String recipient;
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "Content is required")
    private String content;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    private String type; // EMAIL, SMS, PUSH
}
