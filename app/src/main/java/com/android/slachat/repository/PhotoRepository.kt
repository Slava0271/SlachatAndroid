package com.android.slachat.repository

import clean.android.network.either.Either
import com.android.slachat.network.model.UploadPhoto

interface PhotoRepository {
    suspend fun uploadPhoto(uploadPhoto: UploadPhoto): Either<String>

    suspend fun getPhoto(userId : Long) : Either<ByteArray>
}