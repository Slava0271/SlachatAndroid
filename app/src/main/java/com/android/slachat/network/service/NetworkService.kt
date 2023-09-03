package com.android.slachat.network.service

import clean.android.network.either.Either
import clean.android.network.service.ApiAnswerService
import com.android.slachat.network.api.NetworkApiService
import com.android.slachat.network.model.LoginEntity
import com.android.slachat.network.model.UploadPhoto
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.network.response.TokenModel

class NetworkService(
    private val service: NetworkApiService,
    private val apiAnswerService: ApiAnswerService,
) {

    suspend fun login(entity: LoginEntity): Either<TokenModel> {
        return try {
            val response = service.login(entity)
            apiAnswerService.extract(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getMyChats(): Either<List<MyChatItemModel>> {
        return try {
            val response = service.getMyChats()
            apiAnswerService.extract(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun uploadPhoto(uploadPhoto: UploadPhoto): Either<String> {
        return try {
            val response = service.uploadPhoto(uploadPhoto.file)
            apiAnswerService.extract(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getUserPhoto(userId: Long): Either<ByteArray> {
        return try {
            val response = service.getUserPhoto(userId.toString())
            apiAnswerService.extract(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }
}