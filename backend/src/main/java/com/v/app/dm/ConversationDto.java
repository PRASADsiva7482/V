package com.v.app.dm;

import com.v.app.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationDto {
    private Long id;
    private UserDto otherUser;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
}
