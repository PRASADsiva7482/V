package com.v.app.domain.repository

import com.v.app.common.Resource
import com.v.app.data.remote.VApi
import com.v.app.data.remote.dto.ConversationDto
import com.v.app.data.remote.dto.CreateConversationRequest
import com.v.app.data.remote.dto.MessageDto
import com.v.app.data.remote.dto.SendMessageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DMRepository @Inject constructor(
    private val api: VApi
) {
    suspend fun getConversations(): Flow<Resource<List<ConversationDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getConversations()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load conversations"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    suspend fun createConversation(participantId: Long): Flow<Resource<ConversationDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.createConversation(CreateConversationRequest(participantId))
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to create conversation"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    suspend fun getMessages(conversationId: Long): Flow<Resource<List<MessageDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getMessages(conversationId)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load messages"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    suspend fun sendMessage(conversationId: Long, content: String): Flow<Resource<MessageDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.sendMessage(conversationId, SendMessageRequest(content))
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to send message"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }
}
