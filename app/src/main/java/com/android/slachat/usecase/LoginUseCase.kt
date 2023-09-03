package com.android.slachat.usecase

import clean.android.network.connection.ConnectionManager
import clean.android.network.either.Either
import clean.android.network.exception.ConnectionException
import com.android.slachat.network.model.LoginEntity
import com.android.slachat.network.response.TokenModel
import com.android.slachat.network.service.NetworkService
import com.android.slachat.usecase.base.AsyncUseCase
import org.koin.core.component.inject


class LoginUseCase : AsyncUseCase<TokenModel, LoginEntity>() {
    private val networkService: NetworkService by inject()
    private val connectionManager: ConnectionManager by inject()

    override suspend fun run(params: LoginEntity): Either<TokenModel> {
        return if (!connectionManager.isConnected()) Either.failure(ConnectionException())
        else networkService.login(params)
    }
}