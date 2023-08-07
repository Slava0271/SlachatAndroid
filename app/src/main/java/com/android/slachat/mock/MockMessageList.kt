package com.android.slachat.mock

import com.android.slachat.data.MessageModel

class MockMessageList {
    companion object {
        fun getData() = MockMessageList().messageModelList
    }

    private val messages = listOf(
        "Hi, hope you have nice day",
        "Can you provide what are you talking about",
        "Sure, i'll call you a bit latter",
        "See you soon!",
        "How you noting today ? ",
        "I've hear that you bought a new car, that's true ?"
    )
    private val userNames = listOf("Nikolay", "Slavuha", "Voldemar", "Ryan Gosling")
    private val messageModelList = listOf(
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random()),
        MessageModel( "14:05", messages.random())
    )
}