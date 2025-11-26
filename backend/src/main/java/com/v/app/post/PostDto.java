package com.v.app.post;

import com.v.app.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private UserDto user;
    private String content;
    private String mediaUrl;
    private LocalDateTime createdAt;

    // Relations
    private PostDto replyToPost;
    private PostDto quotePost;

    // Counts
    private long likesCount;
    private long repostsCount;
    private long repliesCount;

    // Context
    private boolean isLiked;
    private boolean isReposted;
    private boolean isBookmarked;
}
