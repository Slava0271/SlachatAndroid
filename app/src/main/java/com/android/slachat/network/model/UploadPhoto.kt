package com.android.slachat.network.model

import okhttp3.MultipartBody

data class UploadPhoto(
    val file: MultipartBody.Part
)