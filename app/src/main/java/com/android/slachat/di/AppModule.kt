package com.android.slachat.di

import com.android.slachat.presentation.ChatListPresentation
import com.android.slachat.presentation.SignInPresentation
import com.android.slachat.repository.ChatListRepository
import com.android.slachat.repository.impl.ChatListRepositoryImpl
import com.android.slachat.viewmodel.ChatListViewModel
import com.android.slachat.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { ChatListViewModel() }
    factory<SignInPresentation> { LoginViewModel() }
    factory<ChatListPresentation> { ChatListViewModel() }
    factory<ChatListRepository> { ChatListRepositoryImpl() }
}