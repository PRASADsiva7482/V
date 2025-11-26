package com.v.app.ui

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Long) = "profile/$userId"
    }
    object PostDetails : Screen("post/{postId}") {
        fun createRoute(postId: Long) = "post/$postId"
    }
    object CreatePost : Screen("create_post")
    object Chat : Screen("chat/{conversationId}") {
        fun createRoute(conversationId: Long) = "chat/$conversationId"
    }
    object Search : Screen("search")
    object Notifications : Screen("notifications")
    object DMs : Screen("dms")
}
