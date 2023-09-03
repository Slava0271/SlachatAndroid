package com.android.slachat.usecase

import com.android.slachat.network.model.UploadPhoto
import com.android.slachat.repository.PhotoRepository
import com.android.slachat.usecase.base.AsyncUseCase
import org.koin.core.component.inject

class UploadPhotoUseCase : AsyncUseCase<String, UploadPhoto>() {
    private val photoRepository: PhotoRepository by inject()
    override suspend fun run(params: UploadPhoto) = photoRepository.uploadPhoto(params)
}