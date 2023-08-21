package com.android.slachat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import clean.android.network.either.onFailure
import clean.android.network.either.onSuccess
import clean.android.network.repository.ResponseErrorsRepository
import com.android.slachat.data.ChatItemModel
import com.android.slachat.mapper.Mapper
import com.android.slachat.mock.MockChatList
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.presentation.ChatListPresentation
import com.android.slachat.repository.ChatListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatListViewModel : ViewModel(), KoinComponent, ChatListPresentation,
    Mapper<MyChatItemModel, ChatItemModel> {
    private val chatListRepository: ChatListRepository by inject()
    private val responseErrorsRepository: ResponseErrorsRepository by inject()


    fun getList(): Flow<List<ChatItemModel>> = flow {
        val result = chatListRepository.getList()
        val list = mutableListOf<ChatItemModel>()

        result.onSuccess {
            it.forEach { item ->
                list.add(mapModel(item))
            }
            emit(list)
        }

        result.onFailure {
            responseErrorsRepository.extractError(it)
        }
    }.flowOn(Dispatchers.IO)


    override fun onItemClick(navController: NavController) {
        navController.navigate("conversation")
    }

    override fun onAddItemClick() {

    }

    override fun mapModel(model: MyChatItemModel) = ChatItemModel(
        chatId = model.chatId,
        imageUrl = MockChatList.getRandomImage(),
        username = model.user.username,
        lastMessage = "Last",
        messageTime = "22:04"
    )
}