package com.android.slachat.repository.impl

import clean.android.network.connection.ConnectionManager
import clean.android.network.either.Either
import clean.android.network.exception.ConnectionException
import com.android.slachat.network.model.UploadPhoto
import com.android.slachat.network.service.NetworkService
import com.android.slachat.repository.PhotoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PhotoRepositoryImpl : PhotoRepository, KoinComponent {
    private val networkService: NetworkService by inject()
    private val connectionManager: ConnectionManager by inject()

    override suspend fun uploadPhoto(uploadPhoto: UploadPhoto): Either<String> {
        if (!connectionManager.isConnected()) return Either.failure(ConnectionException())
        return networkService.uploadPhoto(uploadPhoto)
    }

    override suspend fun getPhoto(userId: Long): Either<ByteArray> {
        if (!connectionManager.isConnected()) return Either.failure(ConnectionException())
        return networkService.getUserPhoto(userId)
    }
}