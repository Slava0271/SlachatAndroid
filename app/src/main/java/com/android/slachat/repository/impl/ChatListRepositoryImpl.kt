package com.android.slachat.repository.impl

import com.android.slachat.data.ChatItemModel
import com.android.slachat.mock.MockChatList
import com.android.slachat.repository.ChatListRepository

class ChatListRepositoryImpl : ChatListRepository {
    override fun getList(): List<ChatItemModel> = MockChatList.getData()

}