package com.android.slachat.repository.impl

import com.android.slachat.data.ChatItemModel
import com.android.slachat.repository.ChatListRepository

class ChatListRepositoryImpl : ChatListRepository {
    override fun getList(): List<ChatItemModel> = defaultList


    private val defaultList = listOf(
        ChatItemModel(
            "https://www.webbox.co.uk/wp-content/uploads/2020/10/angry_cat_2-scaled.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ),
        ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ),
        ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ),
        ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ),
        ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ), ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ),
        ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ), ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ), ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ), ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ), ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        ), ChatItemModel(
            "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg", "Kotik", "" +
                    "Myau-myau", "10:20"
        )
    )
}