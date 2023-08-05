package com.android.slachat.repository

import com.android.slachat.data.MessageModel

interface MessagesRepository {
    fun getListMessage(): List<MessageModel>
}