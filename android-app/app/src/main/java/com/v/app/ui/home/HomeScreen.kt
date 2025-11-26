package com.v.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource
import com.v.app.ui.Screen
import com.v.app.ui.components.PostItem
import com.v.app.ui.post.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    val timelineState by viewModel.homeTimelineState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.CreatePost.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Create Post")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = timelineState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Error -> {
                    Text(
                        text = state.message ?: "Error loading timeline",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.data ?: emptyList()) { post ->
                            PostItem(
                                post = post,
                                onPostClick = { postId ->
                                    navController.navigate(Screen.PostDetails.createRoute(postId))
                                },
                                onLikeClick = { postId ->
                                    viewModel.likePost(postId)
                                },
                                onRepostClick = { postId ->
                                    // viewModel.repostPost(postId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
