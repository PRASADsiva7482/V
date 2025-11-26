package com.v.app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.app.common.Resource
import com.v.app.data.remote.dto.UserDto
import com.v.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.v.app.data.remote.dto.PostDto
import com.v.app.domain.repository.PostRepository

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<Resource<UserDto>?>(null)
    val profileState: StateFlow<Resource<UserDto>?> = _profileState.asStateFlow()

    private val _userTimelineState = MutableStateFlow<Resource<List<PostDto>>>(Resource.Loading())
    val userTimelineState: StateFlow<Resource<List<PostDto>>> = _userTimelineState.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                _profileState.value = it
                if (it is Resource.Success) {
                    loadUserTimeline(it.data!!.id)
                }
            }
        }
    }
    
    fun loadUserTimeline(userId: Long) {
        viewModelScope.launch {
            postRepository.getUserTimeline(userId).collect {
                _userTimelineState.value = it
            }
        }
    }
}
