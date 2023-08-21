package com.android.slachat.repository

import clean.android.network.either.Either
import com.android.slachat.network.response.MyChatItemModel

interface ChatListRepository {
    suspend fun getList(): Either<List<MyChatItemModel>>
}