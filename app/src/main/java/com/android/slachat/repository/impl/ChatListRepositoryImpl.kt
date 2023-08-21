package com.android.slachat.repository.impl

import clean.android.network.connection.ConnectionManager
import clean.android.network.either.Either
import clean.android.network.exception.ConnectionException
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.network.service.NetworkService
import com.android.slachat.repository.ChatListRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatListRepositoryImpl : ChatListRepository, KoinComponent {
    private val networkService: NetworkService by inject()
    private val connectionManager: ConnectionManager by inject()

    override suspend fun getList(): Either<List<MyChatItemModel>> {
        return if (!connectionManager.isConnected()) return Either.failure(ConnectionException())
        else networkService.getMyChats()
    }

}