package com.v.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.v.app.common.Resource
import com.v.app.data.remote.dto.PostDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = profileState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Error -> {
                    Text(
                        text = state.message ?: "Error loading profile",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    val user = state.data!!
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Banner
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(Color.Gray)
                        ) {
                            if (user.bannerUrl != null) {
                                AsyncImage(
                                    model = user.bannerUrl,
                                    contentDescription = "Banner",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        
                        // Avatar & Info
                        Column(modifier = Modifier.padding(16.dp)) {
                            AsyncImage(
                                model = user.avatarUrl ?: "https://via.placeholder.com/150",
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(80.dp)
                                    .offset(y = (-50).dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                            )
                            
                            Text(
                                text = user.displayName,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "@${user.username}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            if (user.bio != null) {
                                Text(text = user.bio)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            
                            Row {
                                Text(text = "${user.followingCount} Following")
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = "${user.followersCount} Followers")
                            }
                        }
                        
                        // Timeline
                        val timelineState by viewModel.userTimelineState.collectAsState()
                        when (val tState = timelineState) {
                            is Resource.Success -> {
                                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                    items(tState.data ?: emptyList()) { post ->
                                        com.v.app.ui.components.PostItem(
                                            post = post,
                                            onPostClick = { navController.navigate(com.v.app.ui.Screen.PostDetails.createRoute(post.id)) },
                                            onLikeClick = {},
                                            onRepostClick = {}
                                        )
                                    }
                                }
                            }
                            is Resource.Loading -> {
                                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                            else -> {}
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
