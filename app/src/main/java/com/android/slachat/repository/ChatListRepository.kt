package com.android.slachat.repository

import com.android.slachat.data.ChatItemModel

interface ChatListRepository {
    fun getList(): List<ChatItemModel>
}