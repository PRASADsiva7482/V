package com.v.app.data.repository

import com.v.app.common.Resource
import com.v.app.data.remote.VApi
import com.v.app.data.remote.dto.AuthRequest
import com.v.app.data.remote.dto.AuthResponse
import com.v.app.data.remote.dto.RegisterRequest
import com.v.app.data.remote.dto.UserDto
import com.v.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: VApi
) : AuthRepository {

    override suspend fun login(request: AuthRequest): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.login(request)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Login failed"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun signup(request: RegisterRequest): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.signup(request)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Signup failed"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getCurrentUser(): Flow<Resource<UserDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCurrentUser()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()!!.data!!))
            } else {
                emit(Resource.Error(response.body()?.message ?: "Failed to fetch profile"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
