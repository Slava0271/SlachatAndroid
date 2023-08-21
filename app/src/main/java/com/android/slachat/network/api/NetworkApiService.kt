package com.android.slachat.network.api

import clean.android.network.service.ApiService
import com.android.slachat.network.model.LoginEntity
import com.android.slachat.network.response.MyChatItemModel
import com.android.slachat.network.response.TokenModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApiService : ApiService {
    @POST("/users/login")
    suspend fun login(@Body loginEntity: LoginEntity): Response<TokenModel>

    @GET("chats/my-chats")
    suspend fun getMyChats() : Response<List<MyChatItemModel>>
}