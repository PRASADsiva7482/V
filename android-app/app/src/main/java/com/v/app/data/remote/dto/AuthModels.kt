package com.v.app.data.remote.dto

data class AuthRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto
)

data class UserDto(
    val id: Long,
    val username: String,
    val displayName: String,
    val bio: String?,
    val avatarUrl: String?,
    val bannerUrl: String?,
    val followersCount: Long,
    val followingCount: Long,
    val isFollowing: Boolean
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)
