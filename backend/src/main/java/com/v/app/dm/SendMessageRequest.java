package com.v.app.dm;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long recipientId; // For creating conversation
    private Long conversationId; // For existing conversation
    private String content;
}
