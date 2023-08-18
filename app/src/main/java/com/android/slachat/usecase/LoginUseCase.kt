package com.android.slachat.usecase

//import com.android.slachat.either.Either
//import com.android.slachat.network.model.LoginModel
//import com.android.slachat.network.repository.UserRepository
//import com.android.slachat.usecase.base.AsyncUseCase
//import org.koin.core.component.inject
//
//class LoginUseCase : AsyncUseCase<Any, LoginModel>() {
//    private val userRepository: UserRepository by inject()
//    override suspend fun run(params: LoginModel): Either<Any> {
//        return Either.success(userRepository.login(params))
//    }
//}