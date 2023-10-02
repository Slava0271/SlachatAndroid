package com.android.slachat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.slachat.data.ConversationUserModel
import com.android.slachat.data.MessageModel
import com.android.slachat.mapper.Mapper
import com.android.slachat.repository.MessagesRepository
import com.android.slachat.ui.conversation.model.ConversationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConversationViewModel : ViewModel(), KoinComponent, Mapper<MessageModel, ConversationModel> {
    private val messagesRepository: MessagesRepository by inject()

    private val _newMessageEvent = MutableSharedFlow<String>()
    val newMessageEvent: Flow<String> = _newMessageEvent


    internal val model = ConversationUserModel(
        "Kotik 228",
        true,
        image = "https://www.webbox.co.uk/wp-content/uploads/2020/10/angry_cat_2-scaled.jpg"
    )

    init {
        viewModelScope.launch() {
            connectToServer()
          //  emitNewMessage()
        }
    }

    private suspend fun emitNewMessage() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _newMessageEvent.emit("New message !")
            }
        }
    }


    private suspend fun connectToServer() {
        val host = "192.168.0.100"
        val port = 8082

        try {
            withContext(Dispatchers.IO) {
                Socket(host, port).use { socket ->
                    PrintWriter(socket.getOutputStream(), true).use { out ->
                        BufferedReader(InputStreamReader(socket.getInputStream())).use { inStream ->
                            var response = inStream.readLine()
                            while (true) {
                                delay(500)
                                val message = inStream.readLine()
                                if (message != null) {
                                    response = message
                                    _newMessageEvent.emit(response)
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }

    fun getCurrentTime(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(currentTime))
    }


    private fun getList() = messagesRepository.getListMessage()
    fun getParsedData(): List<ConversationModel> = getList().map { mapModel(it) }

    override fun mapModel(model: MessageModel) =
        ConversationModel(this.model.userName, model.time, model.message)
}