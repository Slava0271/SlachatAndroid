package ua.kr.android.restaurant.network.entities.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataResponse<Data>(
    @SerializedName("data")
    val data: Data? = null
) : Serializable