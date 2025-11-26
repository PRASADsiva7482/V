package com.v.app.data.repository

import com.v.app.common.Resource
import com.v.app.data.remote.VApi
import com.v.app.data.remote.dto.CreatePostRequest
import com.v.app.data.remote.dto.PostDto
import com.v.app.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: VApi
) : PostRepository {

    override suspend fun createPost(
        content: String,
        mediaUrl: String?,
        replyToPostId: Long?,
        quotePostId: Long?
    ): Flow<Resource<PostDto>> = flow {
        emit(Resource.Loading())
        try {
            val request = CreatePostRequest(content, mediaUrl, replyToPostId, quotePostId)
            val response = api.createPost(request)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to create post"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getHomeTimeline(): Flow<Resource<List<PostDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getHomeTimeline()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load timeline"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getUserTimeline(userId: Long): Flow<Resource<List<PostDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getUserTimeline(userId)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load timeline"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getPost(postId: Long): Flow<Resource<PostDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getPost(postId)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load post"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getReplies(postId: Long): Flow<Resource<List<PostDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getReplies(postId)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load replies"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun likePost(postId: Long): Flow<Resource<Unit>> = flow {
        try {
            val response = api.likePost(postId)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Failed to like post"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    override suspend fun unlikePost(postId: Long): Flow<Resource<Unit>> = flow {
        try {
            val response = api.unlikePost(postId)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Failed to unlike post"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }
    
    override suspend fun repostPost(postId: Long): Flow<Resource<Unit>> = flow {
        try {
            val response = api.repostPost(postId)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Failed to repost"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    override suspend fun unrepostPost(postId: Long): Flow<Resource<Unit>> = flow {
        try {
            val response = api.unrepostPost(postId)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Failed to unrepost"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }
}
