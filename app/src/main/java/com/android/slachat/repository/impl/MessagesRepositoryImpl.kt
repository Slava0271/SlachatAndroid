package com.android.slachat.repository.impl

import com.android.slachat.mock.MockMessageList
import com.android.slachat.repository.MessagesRepository

class MessagesRepositoryImpl : MessagesRepository {
    override fun getListMessage() = MockMessageList.getData()
}