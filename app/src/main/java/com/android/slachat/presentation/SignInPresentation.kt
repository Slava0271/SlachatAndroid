package com.android.slachat.presentation

interface SignInPresentation {
    fun forgotPassword()

    fun signIn()

    fun checkFields(login: String, password: String) : Boolean
}