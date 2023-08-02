package com.android.slachat.presentation

import androidx.navigation.NavController
import com.android.slachat.data.SignInModel

interface SignInPresentation {
    fun forgotPassword()

    fun signIn(signInModel: SignInModel, navController: NavController)

}