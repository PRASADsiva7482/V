package com.v.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.app.common.Resource
import com.v.app.data.remote.dto.AuthRequest
import com.v.app.data.remote.dto.AuthResponse
import com.v.app.data.remote.dto.RegisterRequest
import com.v.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<AuthResponse>?>(null)
    val authState: StateFlow<Resource<AuthResponse>?> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(AuthRequest(email, password)).collect {
                _authState.value = it
            }
        }
    }

    fun signup(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            repository.signup(RegisterRequest(email, password, displayName)).collect {
                _authState.value = it
            }
        }
    }
    
    fun resetState() {
        _authState.value = null
    }
}
