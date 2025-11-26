package com.v.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.app.common.Resource
import com.v.app.data.remote.dto.PostDto
import com.v.app.data.remote.dto.UserDto
import com.v.app.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _userResults = MutableStateFlow<Resource<List<UserDto>>?>(null)
    val userResults: StateFlow<Resource<List<UserDto>>?> = _userResults.asStateFlow()

    private val _postResults = MutableStateFlow<Resource<List<PostDto>>?>(null)
    val postResults: StateFlow<Resource<List<PostDto>>?> = _postResults.asStateFlow()

    fun searchUsers(query: String) {
        viewModelScope.launch {
            repository.searchUsers(query).collect {
                _userResults.value = it
            }
        }
    }

    fun searchPosts(query: String) {
        viewModelScope.launch {
            repository.searchPosts(query).collect {
                _postResults.value = it
            }
        }
    }
}
