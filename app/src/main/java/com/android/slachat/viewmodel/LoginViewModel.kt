package com.android.slachat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import clean.android.network.either.onFailure
import clean.android.network.either.onSuccess
import com.android.slachat.data.SignInModel
import com.android.slachat.network.model.LoginEntity
import com.android.slachat.network.service.NetworkService
import com.android.slachat.presentation.SignInPresentation
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : ViewModel(), SignInPresentation, KoinComponent {
    private val networkService: NetworkService by inject()

    init {
        viewModelScope.launch {
            val result = networkService.login(LoginEntity("Slava200271", "Horosh_200271"))
            result.onSuccess {
                Log.d("showInfo", "$it")
            }
            result.onFailure {
                Log.d("showInfo", "$it")
            }
        }
    }

    override fun forgotPassword() {

    }

    override fun signIn(signInModel: SignInModel, navController: NavController) {
        if (checkFields(signInModel)) {
            navController.navigate("chatItems")
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


}