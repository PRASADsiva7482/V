package com.v.app.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.app.common.Resource
import com.v.app.data.remote.dto.PostDto
import com.v.app.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _homeTimelineState = MutableStateFlow<Resource<List<PostDto>>>(Resource.Loading())
    val homeTimelineState: StateFlow<Resource<List<PostDto>>> = _homeTimelineState.asStateFlow()

    private val _createPostState = MutableStateFlow<Resource<PostDto>?>(null)
    val createPostState: StateFlow<Resource<PostDto>?> = _createPostState.asStateFlow()

    private val _postDetailState = MutableStateFlow<Resource<PostDto>?>(null)
    val postDetailState: StateFlow<Resource<PostDto>?> = _postDetailState.asStateFlow()

    private val _repliesState = MutableStateFlow<Resource<List<PostDto>>>(Resource.Loading())
    val repliesState: StateFlow<Resource<List<PostDto>>> = _repliesState.asStateFlow()

    init {
        loadHomeTimeline()
    }

    fun loadHomeTimeline() {
        viewModelScope.launch {
            repository.getHomeTimeline().collect {
                _homeTimelineState.value = it
            }
        }
    }

    fun loadPostDetails(postId: Long) {
        viewModelScope.launch {
            repository.getPost(postId).collect {
                _postDetailState.value = it
            }
            repository.getReplies(postId).collect {
                _repliesState.value = it
            }
        }
    }

    fun createPost(content: String, mediaUrl: String? = null) {
        viewModelScope.launch {
            repository.createPost(content, mediaUrl, null, null).collect {
                _createPostState.value = it
                if (it is Resource.Success) {
                    loadHomeTimeline() // Refresh timeline
                }
            }
        }
    }

    fun likePost(postId: Long) {
        viewModelScope.launch {
            repository.likePost(postId).collect {
                // Optimistic update or refresh could be implemented here
                if (it is Resource.Success) {
                    loadHomeTimeline()
                }
            }
        }
    }
    
    fun resetCreatePostState() {
        _createPostState.value = null
    }
}
