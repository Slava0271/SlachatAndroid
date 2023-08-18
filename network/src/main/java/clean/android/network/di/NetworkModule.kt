package clean.android.network.di

import clean.android.network.api.NetworkProvider
import clean.android.network.service.ApiAnswerService
import clean.android.network.service.ApiErrorParser
import org.koin.dsl.module


val networkModule = module {
    single { NetworkProvider.provideGson() }
    single { ApiErrorParser() }
    single { ApiAnswerService(get()) }
}