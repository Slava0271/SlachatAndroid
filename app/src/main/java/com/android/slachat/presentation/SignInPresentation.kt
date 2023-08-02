package com.android.slachat.presentation

import com.android.slachat.data.SignInModel

interface SignInPresentation {
    fun forgotPassword()

    fun signIn()

    fun checkFields(signInModel: SignInModel): Boolean
}