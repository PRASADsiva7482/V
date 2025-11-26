package com.v.app.post;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String content;
    private String mediaUrl;
    private Long replyToPostId;
    private Long quotePostId;
}
