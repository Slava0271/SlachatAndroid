package com.android.slachat.mock

class MockChatList {
    companion object {
        fun getData() = MockChatList().defaultList

        fun getRandomImage() = MockChatList().imagesList().random()
    }

    private val userNames = listOf("Nikolay", "Slavuha", "Voldemar", "Ryan Gosling")


    private val defaultList = emptyList<Any>()
//    private val defaultList = listOf(
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//        ChatItemModel(
//            imagesList().random(),
//            userNames.random(),
//            "" +
//                    "Myau-myau",
//            "10:20"
//        ),
//    )

    private fun imagesList() = listOf(
        "https://media-cldnry.s-nbcnews.com/image/upload/t_fit-1240w,f_auto,q_auto:best/rockcms/2022-08/220805-domestic-cat-mjf-1540-382ba2.jpg",
        "https://www.webbox.co.uk/wp-content/uploads/2020/10/angry_cat_2-scaled.jpg",
        "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png",
        "https://www.webbox.co.uk/wp-content/uploads/2020/10/angry_cat_2-scaled.jpg"
    )
}