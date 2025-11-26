package com.v.app.notification;

import com.v.app.user.UserDto;
import com.v.app.user.UserEntity;
import com.v.app.user.UserRepository;
import com.v.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final UserRepository userRepository; // For actor mapping if needed, or just use entity

    @Transactional
    public void createNotification(Long recipientId, Long actorId, String type, Long entityId) {
        if (recipientId.equals(actorId))
            return; // Don't notify self

        NotificationEntity notification = new NotificationEntity();
        notification.setRecipient(userRepository.getReferenceById(recipientId));
        notification.setActor(userRepository.getReferenceById(actorId));
        notification.setType(type);
        notification.setEntityId(entityId);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    public Page<NotificationDto> listNotifications(Pageable pageable) {
        UserEntity currentUser = userService.getCurrentUserEntity();
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(currentUser.getId(), pageable)
                .map(this::mapToDto);
    }

    @Transactional
    public void markAsRead(Long id) {
        UserEntity currentUser = userService.getCurrentUserEntity();
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getRecipient().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized");
        }

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    private NotificationDto mapToDto(NotificationEntity entity) {
        NotificationDto dto = new NotificationDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setEntityId(entity.getEntityId());
        dto.setRead(entity.isRead());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getActor() != null) {
            UserDto actorDto = new UserDto();
            actorDto.setId(entity.getActor().getId());
            actorDto.setUsername(entity.getActor().getUsername());
            actorDto.setAvatarUrl(entity.getActor().getAvatarUrl());
            dto.setActor(actorDto);
        }

        return dto;
    }
}
