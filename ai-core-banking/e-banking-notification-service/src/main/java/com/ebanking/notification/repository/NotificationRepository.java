package com.ebanking.notification.repository;

import com.ebanking.notification.entity.Notification;
import com.ebanking.notification.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByCustomerId(Long customerId, Pageable pageable);
    List<Notification> findByStatus(NotificationStatus status);
}
