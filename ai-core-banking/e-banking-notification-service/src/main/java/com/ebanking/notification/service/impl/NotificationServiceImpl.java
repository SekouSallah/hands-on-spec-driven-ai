package com.ebanking.notification.service.impl;

import com.ebanking.notification.dto.request.SendNotificationRequest;
import com.ebanking.notification.dto.response.NotificationResponse;
import com.ebanking.notification.entity.Notification;
import com.ebanking.notification.enums.NotificationStatus;
import com.ebanking.notification.enums.NotificationType;
import com.ebanking.notification.exception.BusinessRuleException;
import com.ebanking.notification.exception.ResourceNotFoundException;
import com.ebanking.notification.mapper.NotificationMapper;
import com.ebanking.notification.repository.NotificationRepository;
import com.ebanking.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public NotificationResponse sendNotification(SendNotificationRequest request) {
        log.info("Sending notification to {}", request.getRecipient());
        
        NotificationType type = NotificationType.EMAIL;
        if (request.getType() != null) {
            try { type = NotificationType.valueOf(request.getType().toUpperCase()); }
            catch (IllegalArgumentException e) { type = NotificationType.EMAIL; }
        }

        Notification notification = Notification.builder()
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .content(request.getContent())
                .type(type)
                .customerId(request.getCustomerId())
                .status(NotificationStatus.PENDING)
                .build();

        Notification saved = notificationRepository.save(notification);
        return processAndSend(saved);
    }

    @Override
    @Transactional
    public NotificationResponse retryNotification(Long id) {
        Notification notification = findNotificationById(id);
        if (notification.getStatus() == NotificationStatus.SENT) {
            throw new BusinessRuleException("NOTIFICATION_ALREADY_SENT", "This notification has already been sent successfully");
        }
        return processAndSend(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse findById(Long id) {
        return notificationMapper.toResponse(findNotificationById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> findByCustomerId(Long customerId, Pageable pageable) {
        return notificationRepository.findByCustomerId(customerId, pageable).map(notificationMapper::toResponse);
    }

    private NotificationResponse processAndSend(Notification notification) {
        try {
            if (notification.getType() == NotificationType.EMAIL) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(notification.getRecipient());
                message.setSubject(notification.getSubject());
                message.setText(notification.getContent());
                message.setFrom("noreply@ai-banking.com");
                
                mailSender.send(message);
                log.info("Email sent successfully to {}", notification.getRecipient());
            } else {
                // Mock SMS/PUSH sending for workshop
                log.info("Mock sending {} to {}", notification.getType(), notification.getRecipient());
            }
            
            notification.setStatus(NotificationStatus.SENT);
            notification.setErrorMessage(null);
        } catch (Exception e) {
            log.error("Failed to send notification: {}", e.getMessage());
            notification.setStatus(NotificationStatus.FAILED);
            notification.setErrorMessage(e.getMessage());
        }
        
        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    private Notification findNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
    }
}
