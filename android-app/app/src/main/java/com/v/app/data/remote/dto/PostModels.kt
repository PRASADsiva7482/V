package com.v.app.data.remote.dto

import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    val content: String,
    val mediaUrl: String?,
    val createdAt: String, // ISO String
    
    // Relations
    val author: UserDto,
    val replyToPost: PostDto?,
    val quotePost: PostDto?,
    
    // Counts
    val likesCount: Long,
    val repostsCount: Long,
    val repliesCount: Long,
    
    // Context
    val isLiked: Boolean,
    val isReposted: Boolean,
    val isBookmarked: Boolean
)

data class CreatePostRequest(
    val content: String,
    val mediaUrl: String? = null,
    val replyToPostId: Long? = null,
    val quotePostId: Long? = null
)
