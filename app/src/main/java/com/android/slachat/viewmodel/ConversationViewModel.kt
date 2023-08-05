package com.android.slachat.viewmodel

import androidx.lifecycle.ViewModel
import com.android.slachat.data.ConversationUserModel
import com.android.slachat.data.MessageModel
import com.android.slachat.mapper.Mapper
import com.android.slachat.repository.MessagesRepository
import com.android.slachat.ui.conversation.model.ConversationModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConversationViewModel : ViewModel(), KoinComponent, Mapper<MessageModel, ConversationModel> {
    private val messagesRepository: MessagesRepository by inject()

    internal val model = ConversationUserModel(
        "Kotik 228",
        true,
        image = "https://www.webbox.co.uk/wp-content/uploads/2020/10/angry_cat_2-scaled.jpg"
    )

    private fun getList() = messagesRepository.getListMessage()
    fun getParsedData(): List<ConversationModel> = getList().map { mapModel(it) }

    override fun mapModel(model: MessageModel) =
        ConversationModel(this.model.userName, model.time, model.message)
}