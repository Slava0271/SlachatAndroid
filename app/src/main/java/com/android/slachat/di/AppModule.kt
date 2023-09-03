package com.android.slachat.di

import clean.android.network.api.NetworkProvider
import clean.android.network.auth.ApiTokenProvider
import clean.android.network.configprovider.BuildConfigProvider
import clean.android.network.connection.ConnectionManager
import clean.android.network.repository.ResponseErrorsRepository
import clean.android.network.service.ApiAnswerService
import clean.android.network.service.ApiErrorParser
import com.android.slachat.network.api.NetworkApiService
import com.android.slachat.network.service.NetworkService
import com.android.slachat.presentation.ChatListPresentation
import com.android.slachat.presentation.SignInPresentation
import com.android.slachat.repository.ChatListRepository
import com.android.slachat.repository.MessagesRepository
import com.android.slachat.repository.PhotoRepository
import com.android.slachat.repository.impl.BuildConfigProviderImpl
import com.android.slachat.repository.impl.ChatListRepositoryImpl
import com.android.slachat.repository.impl.ImplConnectionManager
import com.android.slachat.repository.impl.MessagesRepositoryImpl
import com.android.slachat.repository.impl.PhotoRepositoryImpl
import com.android.slachat.repository.impl.ResponseErrorsRepositoryImpl
import com.android.slachat.tokenprovider.TokenProvider
import com.android.slachat.usecase.GetPhotoUseCase
import com.android.slachat.usecase.GetUserChatsUseCase
import com.android.slachat.usecase.LoginUseCase
import com.android.slachat.usecase.UploadPhotoUseCase
import com.android.slachat.viewmodel.ChatListViewModel
import com.android.slachat.viewmodel.ConversationViewModel
import com.android.slachat.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { ChatListViewModel() }
    viewModel { ConversationViewModel() }


    factory<SignInPresentation> { LoginViewModel() }
    factory<ChatListPresentation> { ChatListViewModel() }
    factory<ChatListRepository> { ChatListRepositoryImpl() }
    factory<MessagesRepository> { MessagesRepositoryImpl() }
    factory<BuildConfigProvider> { BuildConfigProviderImpl() }
    factory<ApiTokenProvider> { TokenProvider(get()) }
    factory<ConnectionManager> { ImplConnectionManager(get()) }
    factory<ResponseErrorsRepository> { ResponseErrorsRepositoryImpl() }
    factory<PhotoRepository> { PhotoRepositoryImpl() }

    single { NetworkProvider.provideGson() }
    single { ApiErrorParser() }
    single { ApiAnswerService(get()) }
    single { NetworkService(get(), get()) }
    single {
        NetworkProvider.provideApiService(
            get(), get<BuildConfigProvider>().getBaseUrl(), NetworkApiService::class.java
        )
    }
    single { LoginUseCase() }
    single { GetUserChatsUseCase() }
    single { UploadPhotoUseCase() }
    single {GetPhotoUseCase()  }

}