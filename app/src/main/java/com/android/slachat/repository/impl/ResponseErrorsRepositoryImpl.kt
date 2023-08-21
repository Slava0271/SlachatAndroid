package com.android.slachat.repository.impl

import clean.android.network.entity.ErrorEntity
import clean.android.network.exception.ConnectionException
import clean.android.network.repository.ResponseErrorsRepository
import com.google.gson.Gson

class ResponseErrorsRepositoryImpl : ResponseErrorsRepository {
    override fun getErrorCode(error: String?): Int {
        val errorMessage = Gson().fromJson(error, ErrorEntity::class.java)
        return errorMessage.status
    }

    override fun extractError(error: Throwable): String {
        if (error is ConnectionException) return error.message
        val errorMessage = Gson().fromJson(error.message, ErrorEntity::class.java)
        return errorMessage.message
    }
}