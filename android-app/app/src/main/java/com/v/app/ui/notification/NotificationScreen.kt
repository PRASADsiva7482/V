package com.v.app.ui.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Notifications") }) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = notifications) {
                is Resource.Success -> {
                    LazyColumn {
                        items(state.data ?: emptyList()) { notification ->
                            ListItem(
                                headlineContent = { 
                                    Text("${notification.actor.displayName} ${getActionText(notification.type)}") 
                                },
                                supportingContent = { 
                                    if (notification.post != null) {
                                        Text(notification.post.content, maxLines = 1)
                                    }
                                }
                            )
                            Divider()
                        }
                    }
                }
                is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                else -> {}
            }
        }
    }
}

fun getActionText(type: String): String {
    return when (type) {
        "LIKE" -> "liked your post"
        "REPOST" -> "reposted your post"
        "FOLLOW" -> "followed you"
        "REPLY" -> "replied to your post"
        else -> "interacted with you"
    }
}
