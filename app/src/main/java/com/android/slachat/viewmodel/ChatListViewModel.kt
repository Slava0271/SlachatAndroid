package com.android.slachat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.android.slachat.data.ChatItemModel
import com.android.slachat.presentation.ChatListPresentation
import com.android.slachat.repository.ChatListRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatListViewModel : ViewModel(), KoinComponent, ChatListPresentation {
    private val chatListRepository: ChatListRepository by inject()

    fun getList(): List<ChatItemModel> = chatListRepository.getList()
    override fun onItemClick(navController: NavController) {
        navController.navigate("conversation")
    }

    override fun onAddItemClick() {

    }
}