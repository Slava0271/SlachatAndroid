package com.android.slachat.network.response

import java.io.Serializable

data class MyChatItemModel(val chatId: Int, val user: ChatUserModel) : Serializable