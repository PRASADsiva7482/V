package com.v.app.ui.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource
import com.v.app.ui.components.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    navController: NavController,
    postId: Long,
    viewModel: PostViewModel = hiltViewModel()
) {
    val postState by viewModel.postDetailState.collectAsState()
    val repliesState by viewModel.repliesState.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPostDetails(postId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // Main Post
                item {
                    when (val state = postState) {
                        is Resource.Success -> {
                            PostItem(
                                post = state.data!!,
                                onPostClick = { }, // Already here
                                onLikeClick = { viewModel.likePost(state.data.id) },
                                onRepostClick = { }
                            )
                            Divider(thickness = 1.dp)
                        }
                        is Resource.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        is Resource.Error -> {
                            Text(
                                text = state.message ?: "Error loading post",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        else -> {}
                    }
                }

                // Replies Header
                item {
                    Text(
                        text = "Replies",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Replies List
                when (val state = repliesState) {
                    is Resource.Success -> {
                        items(state.data ?: emptyList()) { reply ->
                            PostItem(
                                post = reply,
                                onPostClick = { 
                                    // Navigate to reply details? 
                                    // For now, maybe just stay or reload.
                                    // Let's reload to show that thread.
                                    // navController.navigate(Screen.PostDetails.createRoute(reply.id))
                                    // But we need to handle back stack or just replace.
                                },
                                onLikeClick = { viewModel.likePost(reply.id) },
                                onRepostClick = { }
                            )
                            Divider()
                        }
                    }
                    is Resource.Loading -> {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}
