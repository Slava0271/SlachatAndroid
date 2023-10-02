package com.android.slachat

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.slachat.ui.bottombar.BottomNavigatorBar
import com.android.slachat.ui.chatlist.ChatScreen
import com.android.slachat.ui.conversation.ConversationScreen
import com.android.slachat.ui.login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlachatApp() {
    MaterialTheme {
        val navController = rememberNavController()
        var isShowBottomBar by remember { mutableStateOf(false) }
        Scaffold (bottomBar = {
            if(isShowBottomBar) BottomNavigatorBar()
        }){ padding ->
            padding
                NavHost(
                    navController = navController,
                    startDestination = "login") {
                    composable("login") {
                        isShowBottomBar = false
                        LoginScreen(navController = navController)
                    }
                    composable("chatItems") {
                        isShowBottomBar = true
                        ChatScreen(navController)
                    }
                    composable("conversation/{userId}") { backStackEntry ->
                        isShowBottomBar = false
                        val userId = backStackEntry.arguments?.getString("userId")
                        ConversationScreen(navController, userId)
                    }
                }
            }
    }
}

