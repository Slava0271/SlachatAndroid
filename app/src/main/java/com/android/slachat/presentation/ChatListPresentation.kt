package com.android.slachat.presentation

import androidx.navigation.NavController

interface ChatListPresentation {
    fun onItemClick(navController: NavController,userId : Int)

    fun onAddItemClick()
}