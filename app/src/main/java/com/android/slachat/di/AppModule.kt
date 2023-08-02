package com.android.slachat.di

import com.android.slachat.presentation.SignInPresentation
import com.android.slachat.repository.ChatListRepository
import com.android.slachat.repository.impl.ChatListRepositoryImpl
import com.android.slachat.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    factory<SignInPresentation> { LoginViewModel() }
    factory<ChatListRepository> { ChatListRepositoryImpl() }
}