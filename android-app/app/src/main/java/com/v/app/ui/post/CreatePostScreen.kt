package com.v.app.ui.post

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v.app.common.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    var content by remember { mutableStateOf("") }
    val createPostState by viewModel.createPostState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(createPostState) {
        if (createPostState is Resource.Success) {
            Toast.makeText(context, "Post created!", Toast.LENGTH_SHORT).show()
            viewModel.resetCreatePostState()
            navController.popBackStack()
        } else if (createPostState is Resource.Error) {
            Toast.makeText(context, createPostState?.message ?: "Error", Toast.LENGTH_SHORT).show()
            viewModel.resetCreatePostState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Post") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.createPost(content) },
                        enabled = content.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Post")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("What's happening?") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}
