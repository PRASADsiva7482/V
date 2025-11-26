package com.v.app.ui.dm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.app.common.Resource
import com.v.app.data.remote.dto.ConversationDto
import com.v.app.data.remote.dto.MessageDto
import com.v.app.domain.repository.DMRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DMViewModel @Inject constructor(
    private val repository: DMRepository
) : ViewModel() {

    private val _conversations = MutableStateFlow<Resource<List<ConversationDto>>>(Resource.Loading())
    val conversations: StateFlow<Resource<List<ConversationDto>>> = _conversations.asStateFlow()

    private val _messages = MutableStateFlow<Resource<List<MessageDto>>>(Resource.Loading())
    val messages: StateFlow<Resource<List<MessageDto>>> = _messages.asStateFlow()

    init {
        loadConversations()
    }

    fun loadConversations() {
        viewModelScope.launch {
            repository.getConversations().collect {
                _conversations.value = it
            }
        }
    }

    fun loadMessages(conversationId: Long) {
        viewModelScope.launch {
            repository.getMessages(conversationId).collect {
                _messages.value = it
            }
        }
    }

    fun sendMessage(conversationId: Long, content: String) {
        viewModelScope.launch {
            repository.sendMessage(conversationId, content).collect {
                if (it is Resource.Success) {
                    loadMessages(conversationId) // Refresh
                }
            }
        }
    }
}
