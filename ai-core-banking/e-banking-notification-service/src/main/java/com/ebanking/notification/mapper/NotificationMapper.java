package com.ebanking.notification.mapper;

import com.ebanking.notification.dto.request.SendNotificationRequest;
import com.ebanking.notification.dto.response.NotificationResponse;
import com.ebanking.notification.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);
}
