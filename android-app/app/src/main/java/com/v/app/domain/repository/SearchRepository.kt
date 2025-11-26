package com.v.app.domain.repository

import com.v.app.common.Resource
import com.v.app.data.remote.VApi
import com.v.app.data.remote.dto.PostDto
import com.v.app.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: VApi
) {
    suspend fun searchUsers(query: String): Flow<Resource<List<UserDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.searchUsers(query)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Search failed"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }

    suspend fun searchPosts(query: String): Flow<Resource<List<PostDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.searchPosts(query)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data ?: emptyList()))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Search failed"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }
    }
}
