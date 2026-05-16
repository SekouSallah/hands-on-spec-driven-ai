package com.ebanking.notification.controller;

import com.ebanking.notification.dto.request.SendNotificationRequest;
import com.ebanking.notification.dto.response.NotificationResponse;
import com.ebanking.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Management", description = "Operations for sending emails and notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send a new notification")
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody SendNotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get notifications for a customer")
    public ResponseEntity<Page<NotificationResponse>> findByCustomer(
            @PathVariable Long customerId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(notificationService.findByCustomerId(customerId, pageable));
    }

    @PatchMapping("/{id}/retry")
    @Operation(summary = "Retry a failed notification")
    public ResponseEntity<NotificationResponse> retry(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.retryNotification(id));
    }
}
