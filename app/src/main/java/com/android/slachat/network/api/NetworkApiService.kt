package com.android.slachat.network.api

import clean.android.network.service.ApiService
import com.android.slachat.network.model.LoginEntity
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.network.response.TokenModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface NetworkApiService : ApiService {
    @POST("/users/login")
    suspend fun login(@Body loginEntity: LoginEntity): Response<TokenModel>

    @GET("chats/my-chats")
    suspend fun getMyChats(): Response<List<MyChatItemModel>>

    @Multipart
    @POST("/photos/upload")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): Response<String>

    @GET("photos/user/{userId}")
    suspend fun getUserPhoto(@Path("userId") userId: String) : Response<Any>
}