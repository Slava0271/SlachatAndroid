package com.android.slachat.data

data class ConversationUserModel(
    val userName: String,
    val isOnline: Boolean,
    val lastSeen: String = String(),
    val image: String
)