package com.v.app.ui.dm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    conversationId: Long,
    viewModel: DMViewModel = hiltViewModel()
) {
    var messageText by remember { mutableStateOf("") }
    val messagesState by viewModel.messages.collectAsState()

    LaunchedEffect(conversationId) {
        viewModel.loadMessages(conversationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message...") }
                )
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            viewModel.sendMessage(conversationId, messageText)
                            messageText = ""
                        }
                    }
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = messagesState) {
                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                        reverseLayout = true // Show newest at bottom if we sort correctly, but API returns DESC usually. 
                        // If API returns DESC (newest first), reverseLayout=true puts newest at bottom? No.
                        // Standard chat: Newest at bottom.
                        // If list is [Newest, ..., Oldest], reverseLayout=true renders Newest at bottom. Correct.
                    ) {
                        items(state.data ?: emptyList()) { message ->
                            // Simple bubble
                            // Assuming we knew current user ID to align right/left. 
                            // For now, just simple list.
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Text(
                                    text = message.content,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
                is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                else -> {}
            }
        }
    }
}
