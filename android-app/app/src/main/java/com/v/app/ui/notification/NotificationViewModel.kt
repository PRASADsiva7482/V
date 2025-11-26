package com.v.app.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.app.common.Resource
import com.v.app.data.remote.dto.NotificationDto
import com.v.app.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _notifications = MutableStateFlow<Resource<List<NotificationDto>>>(Resource.Loading())
    val notifications: StateFlow<Resource<List<NotificationDto>>> = _notifications.asStateFlow()

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            repository.getNotifications().collect {
                _notifications.value = it
            }
        }
    }
}
