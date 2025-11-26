package com.v.app.dm;

import com.v.app.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long conversationId;
    private UserDto sender;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;
}
