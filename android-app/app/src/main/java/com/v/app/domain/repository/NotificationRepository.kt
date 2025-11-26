package com.v.app.domain.repository

import com.v.app.common.Resource
import com.v.app.data.remote.VApi
import com.v.app.data.remote.dto.NotificationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val api: VApi
) {
    suspend fun getNotifications(): Flow<Resource<List<NotificationDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getNotifications()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to load notifications"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    suspend fun markAsRead(id: Long): Flow<Resource<Unit>> = flow {
        try {
            val response = api.markNotificationAsRead(id)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Failed"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }
}
