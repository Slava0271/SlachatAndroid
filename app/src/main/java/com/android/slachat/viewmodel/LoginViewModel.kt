package com.android.slachat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import clean.android.network.auth.ApiTokenProvider
import clean.android.network.either.onFailure
import clean.android.network.either.onSuccess
import clean.android.network.repository.ResponseErrorsRepository
import com.android.slachat.data.SignInModel
import com.android.slachat.mapper.Mapper
import com.android.slachat.network.model.LoginEntity
import com.android.slachat.presentation.SignInPresentation
import com.android.slachat.usecase.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : ViewModel(), SignInPresentation, KoinComponent,
    Mapper<SignInModel, LoginEntity> {
    private val loginUseCase: LoginUseCase by inject()
    private val tokenProvider: ApiTokenProvider by inject()
    private val responseErrorsRepository: ResponseErrorsRepository by inject()

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: Flow<String> = _errorEvent


    override fun forgotPassword() {

    }

    override fun signIn(signInModel: SignInModel, navController: NavController) {
        if (checkFields(signInModel)) loginRequest(signInModel, navController)
    }

    private fun loginRequest(signInModel: SignInModel, navController: NavController) {
        viewModelScope.launch {
            val result = loginUseCase.run(mapModel(signInModel))
            result.onSuccess {
                tokenProvider.saveToken(it.token, String())
                navController.navigate("chatItems")
            }
            result.onFailure {
                emitError(it)
            }
        }
    }

    private suspend fun emitError(error: Throwable) {
        _errorEvent.emit(responseErrorsRepository.extractError(error))
        delay(3000)
        hideSnackBar()
    }

    fun hideSnackBar() {
        viewModelScope.launch {
            _errorEvent.emit(String())
        }
    }

    private fun checkFields(signInModel: SignInModel) =
        checkUserField(signInModel.login, signInModel.password)

    private fun checkUserField(login: String, password: String) =
        isValidUsername(login) && isValidPassword(password)

    private fun isValidUsername(username: String): Boolean {
        val minLength = 4
        val maxLength = 20
        val allowedCharacters = Regex("^[a-zA-Z0-9_]+$")

        return username.length in minLength..maxLength && allowedCharacters.matches(username)
    }

    private fun isValidPassword(password: String): Boolean {
        val minLength = 6
        val maxLength = 30
        val hasUppercase = Regex("[A-Z]")
        val hasLowercase = Regex("[a-z]")
        val hasDigit = Regex("\\d")
        val hasSpecialChar = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]")

        return password.length in minLength..maxLength &&
                hasUppercase.containsMatchIn(password) &&
                hasLowercase.containsMatchIn(password) &&
                hasDigit.containsMatchIn(password) &&
                hasSpecialChar.containsMatchIn(password)
    }

    override fun mapModel(model: SignInModel) = LoginEntity(model.login, model.password)
}