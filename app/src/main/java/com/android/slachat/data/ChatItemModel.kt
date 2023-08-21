package com.android.slachat.data

data class ChatItemModel(
    val imageUrl: String,
    val username: String,
    val lastMessage: String,
    val messageTime: String,
    val chatId: Int = 0,
    )