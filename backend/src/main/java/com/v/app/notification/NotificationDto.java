package com.v.app.notification;

import com.v.app.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private UserDto actor;
    private String type;
    private Long entityId;
    private boolean isRead;
    private LocalDateTime createdAt;
}
