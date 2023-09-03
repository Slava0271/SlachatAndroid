package com.android.slachat.usecase

import clean.android.network.either.Either
import com.android.slachat.repository.PhotoRepository
import com.android.slachat.usecase.base.AsyncUseCase
import org.koin.core.component.inject

class GetPhotoUseCase : AsyncUseCase<ByteArray, Long>() {
    private val photoRepository: PhotoRepository by inject()
    override suspend fun run(params: Long): Either<ByteArray> {
        return photoRepository.getPhoto(params)
    }
}