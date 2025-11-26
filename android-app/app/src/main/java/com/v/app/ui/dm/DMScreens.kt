package com.v.app.ui.dm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    navController: NavController,
    viewModel: DMViewModel = hiltViewModel()
) {
    val conversations by viewModel.conversations.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Messages") }) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = conversations) {
                is Resource.Success -> {
                    LazyColumn {
                        items(state.data ?: emptyList()) { conversation ->
                            ListItem(
                                headlineContent = { Text(conversation.participant.displayName) },
                                supportingContent = { Text(conversation.lastMessage ?: "") },
                                modifier = Modifier.clickable { 
                                    navController.navigate(com.v.app.ui.Screen.Chat.createRoute(conversation.id))
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
