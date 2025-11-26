package com.v.app.domain.repository

import com.v.app.common.Resource
import com.v.app.data.remote.dto.CreatePostRequest
import com.v.app.data.remote.dto.PostDto
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun createPost(content: String, mediaUrl: String?, replyToPostId: Long?, quotePostId: Long?): Flow<Resource<PostDto>>
    suspend fun getHomeTimeline(): Flow<Resource<List<PostDto>>>
    suspend fun getUserTimeline(userId: Long): Flow<Resource<List<PostDto>>>
    suspend fun getPost(postId: Long): Flow<Resource<PostDto>>
    suspend fun getReplies(postId: Long): Flow<Resource<List<PostDto>>>
    suspend fun likePost(postId: Long): Flow<Resource<Unit>>
    suspend fun unlikePost(postId: Long): Flow<Resource<Unit>>
    suspend fun repostPost(postId: Long): Flow<Resource<Unit>>
    suspend fun unrepostPost(postId: Long): Flow<Resource<Unit>>
}
