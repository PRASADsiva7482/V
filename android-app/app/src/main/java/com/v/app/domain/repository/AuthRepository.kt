package com.v.app.domain.repository

import com.v.app.data.remote.dto.AuthRequest
import com.v.app.data.remote.dto.AuthResponse
import com.v.app.data.remote.dto.RegisterRequest
import com.v.app.data.remote.dto.UserDto
import com.v.app.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: AuthRequest): Flow<Resource<AuthResponse>>
    suspend fun signup(request: RegisterRequest): Flow<Resource<AuthResponse>>
    suspend fun getCurrentUser(): Flow<Resource<UserDto>>
}
