package com.v.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.v.app.ui.auth.LoginScreen
import com.v.app.ui.auth.RegisterScreen
import com.v.app.ui.dm.ChatScreen
import com.v.app.ui.dm.ConversationListScreen
import com.v.app.ui.home.HomeScreen
import com.v.app.ui.notification.NotificationScreen
import com.v.app.ui.post.CreatePostScreen
import com.v.app.ui.post.PostDetailScreen
import com.v.app.ui.profile.ProfileScreen
import com.v.app.ui.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.PostDetails.route,
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId") ?: return@composable
            PostDetailScreen(navController, postId)
        }
        composable(Screen.CreatePost.route) {
            CreatePostScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(Screen.Notifications.route) {
            NotificationScreen(navController)
        }
        composable(Screen.DMs.route) {
            ConversationListScreen(navController)
        }
        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
        ) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getLong("conversationId") ?: return@composable
            ChatScreen(navController, conversationId)
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(navController)
        }
    }
}
