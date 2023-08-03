package com.android.slachat.presentation

import androidx.navigation.NavController

interface ChatListPresentation {
    fun onItemClick(navController: NavController)

    fun onAddItemClick()
}