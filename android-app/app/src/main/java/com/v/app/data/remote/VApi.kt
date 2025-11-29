package com.v.app.data.remote

import com.v.app.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VApi {
    
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<ApiResponse<AuthResponse>>

    @POST("/api/auth/signup")
    suspend fun signup(@Body request: RegisterRequest): Response<ApiResponse<AuthResponse>>

    @GET("/api/users/me")
    suspend fun getCurrentUser(): Response<ApiResponse<UserDto>>
    
    // Posts
    @POST("/api/posts")
    suspend fun createPost(@Body request: CreatePostRequest): Response<ApiResponse<PostDto>>

    @GET("/api/posts/timeline")
    suspend fun getHomeTimeline(): Response<ApiResponse<List<PostDto>>>

    @GET("/api/posts/user/{userId}")
    suspend fun getUserTimeline(@Path("userId") userId: Long): Response<ApiResponse<List<PostDto>>>
    
    @GET("/api/posts/{postId}")
    suspend fun getPost(@Path("postId") postId: Long): Response<ApiResponse<PostDto>>

    @GET("/api/posts/{postId}/replies")
    suspend fun getReplies(@Path("postId") postId: Long): Response<ApiResponse<List<PostDto>>>

    @POST("/api/posts/{postId}/like")
    suspend fun likePost(@Path("postId") postId: Long): Response<ApiResponse<Void>>

    @DELETE("/api/posts/{postId}/like")
    suspend fun unlikePost(@Path("postId") postId: Long): Response<ApiResponse<Void>>

    @POST("/api/posts/{postId}/repost")
    suspend fun repostPost(@Path("postId") postId: Long): Response<ApiResponse<Void>>

    @DELETE("/api/posts/{postId}/repost")
    suspend fun unrepostPost(@Path("postId") postId: Long): Response<ApiResponse<Void>>
    
    // Search
    @GET("/api/search/users")
    suspend fun searchUsers(@Query("query") query: String): Response<ApiResponse<List<UserDto>>>

    @GET("/api/search/posts")
    suspend fun searchPosts(@Query("query") query: String): Response<ApiResponse<List<PostDto>>>

    // DMs
    @GET("/api/dms/conversations")
    suspend fun getConversations(): Response<ApiResponse<List<ConversationDto>>>

    @POST("/api/dms/conversations")
    suspend fun createConversation(@Body request: CreateConversationRequest): Response<ApiResponse<ConversationDto>>

    @GET("/api/dms/conversations/{conversationId}/messages")
    suspend fun getMessages(@Path("conversationId") conversationId: Long): Response<ApiResponse<List<MessageDto>>>

    @POST("/api/dms/conversations/{conversationId}/messages")
    suspend fun sendMessage(
        @Path("conversationId") conversationId: Long,
        @Body request: SendMessageRequest
    ): Response<ApiResponse<MessageDto>>

    // Notifications
    @GET("/api/notifications")
    suspend fun getNotifications(): Response<ApiResponse<List<NotificationDto>>>

    @POST("/api/notifications/{id}/read")
    suspend fun markNotificationAsRead(@Path("id") id: Long): Response<ApiResponse<Void>>
    
    @GET("/api/health")
    suspend fun healthCheck(): String
}
