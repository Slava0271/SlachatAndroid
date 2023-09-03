package com.android.slachat.usecase

import clean.android.network.either.Either
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.repository.ChatListRepository
import com.android.slachat.usecase.base.AsyncUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUserChatsUseCase : AsyncUseCase<List<MyChatItemModel>, Nothing>(), KoinComponent {
    private val chatListRepository: ChatListRepository by inject()
    override suspend fun run(params: Nothing): Either<List<MyChatItemModel>> {
        return chatListRepository.getList()
    }
}