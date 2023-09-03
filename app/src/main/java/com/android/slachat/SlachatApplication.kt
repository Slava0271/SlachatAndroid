package com.android.slachat

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.slachat.ui.chatlist.ChatScreen
import com.android.slachat.ui.conversation.ConversationScreen
import com.android.slachat.ui.login.LoginScreen

@Composable
fun SlachatApp() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(navController = navController)
            }
            composable("chatItems") { ChatScreen(navController) }
            composable("conversation/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")
                ConversationScreen(navController, userId)
            }
        }
    }
}

