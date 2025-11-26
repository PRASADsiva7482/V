package com.v.app.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource
import com.v.app.ui.Screen
import com.v.app.ui.components.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) }
    val userResults by viewModel.userResults.collectAsState()
    val postResults by viewModel.postResults.collectAsState()

    Scaffold(
        topBar = {
            Column {
                OutlinedTextField(
                    value = query,
                    onValueChange = { 
                        query = it
                        if (it.isNotEmpty()) {
                            if (selectedTab == 0) viewModel.searchPosts(it) else viewModel.searchUsers(it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text("Search V") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true
                )
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                        Text("Posts", modifier = Modifier.padding(16.dp))
                    }
                    Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                        Text("People", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (selectedTab == 0) {
                // Posts List
                when (val state = postResults) {
                    is Resource.Success -> {
                        LazyColumn {
                            items(state.data ?: emptyList()) { post ->
                                PostItem(
                                    post = post,
                                    onPostClick = { navController.navigate(Screen.PostDetails.createRoute(post.id)) },
                                    onLikeClick = {},
                                    onRepostClick = {}
                                )
                            }
                        }
                    }
                    is Resource.Loading -> CircularProgressIndicator()
                    else -> {}
                }
            } else {
                // Users List (Simplified)
                when (val state = userResults) {
                    is Resource.Success -> {
                        LazyColumn {
                            items(state.data ?: emptyList()) { user ->
                                ListItem(
                                    headlineContent = { Text(user.displayName) },
                                    supportingContent = { Text("@${user.username}") },
                                    modifier = Modifier.clickable { navController.navigate(Screen.Profile.createRoute(user.id)) }
                                )
                            }
                        }
                    }
                    is Resource.Loading -> CircularProgressIndicator()
                    else -> {}
                }
            }
        }
    }
}
