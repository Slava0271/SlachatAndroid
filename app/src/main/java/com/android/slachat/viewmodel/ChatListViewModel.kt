package com.android.slachat.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import clean.android.network.either.onFailure
import clean.android.network.either.onSuccess
import clean.android.network.repository.ResponseErrorsRepository
import com.android.slachat.data.ChatItemModel
import com.android.slachat.mapper.Mapper
import com.android.slachat.network.model.UploadPhoto
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.network.service.NetworkService
import com.android.slachat.presentation.ChatListPresentation
import com.android.slachat.repository.ChatListRepository
import com.android.slachat.usecase.GetPhotoUseCase
import com.android.slachat.usecase.UploadPhotoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.InputStream

class ChatListViewModel : ViewModel(), KoinComponent, ChatListPresentation,
    Mapper<MyChatItemModel, ChatItemModel> {
    private val chatListRepository: ChatListRepository by inject()
    private val responseErrorsRepository: ResponseErrorsRepository by inject()
    private val uploadPhotoUseCase: UploadPhotoUseCase by inject()
    private val getPhotoUseCase: GetPhotoUseCase by inject()
    private val context: Context by inject()
    private val networkService: NetworkService by inject()

    fun getList(): Flow<List<ChatItemModel>> = flow {
        val result = chatListRepository.getList()
        val list = mutableListOf<ChatItemModel>()

        result.onSuccess {
            it.forEach { item ->
                list.add(mapModel(item))
                emit(list)
            }

            result.onFailure {
                responseErrorsRepository.extractError(it)
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun createFilePartFromUri(uri: Uri): MultipartBody.Part? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val bytes = inputStream.readBytes()
                    inputStream.close()

                    return@withContext MultipartBody.Part.createFormData(
                        "file",
                        "photo.jpg",
                        bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext null
        }
    }

    fun uploadPhoto(uri: Uri) {
        viewModelScope.launch {
            val filePart = createFilePartFromUri(uri)
            if (filePart != null) {
                val result = uploadPhotoUseCase.run(UploadPhoto(filePart))
                result.onSuccess {

                }
                result.onFailure {
                    responseErrorsRepository.extractError(it)
                }
            }
        }
    }

    override fun onItemClick(navController: NavController, userId: Int) {
        navController.navigate("conversation/$userId")
    }

    override fun onAddItemClick() {

    }


    override fun mapModel(model: MyChatItemModel): ChatItemModel {
        return ChatItemModel(model.user.id, model.user.username, "xui123", "1111", model.chatId)
    }

}