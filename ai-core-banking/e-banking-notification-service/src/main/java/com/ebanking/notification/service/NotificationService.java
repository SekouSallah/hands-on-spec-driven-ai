package com.ebanking.notification.service;

import com.ebanking.notification.dto.request.SendNotificationRequest;
import com.ebanking.notification.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    NotificationResponse sendNotification(SendNotificationRequest request);
    NotificationResponse retryNotification(Long id);
    NotificationResponse findById(Long id);
    Page<NotificationResponse> findByCustomerId(Long customerId, Pageable pageable);
}
