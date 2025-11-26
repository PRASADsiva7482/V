package com.v.app.data.remote.dto

import java.time.LocalDateTime

data class ConversationDto(
    val id: Long,
    val participant: UserDto,
    val lastMessage: String?,
    val lastMessageAt: String?,
    val unreadCount: Int
)

data class MessageDto(
    val id: Long,
    val senderId: Long,
    val content: String,
    val createdAt: String
)

data class SendMessageRequest(
    val content: String
)

data class CreateConversationRequest(
    val participantId: Long
)

data class NotificationDto(
    val id: Long,
    val type: String, // LIKE, REPOST, FOLLOW, REPLY
    val actor: UserDto,
    val post: PostDto?,
    val createdAt: String,
    val isRead: Boolean
)
