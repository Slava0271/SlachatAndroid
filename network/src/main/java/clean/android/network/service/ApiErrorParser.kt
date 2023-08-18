package clean.android.network.service

import clean.android.network.exception.ApiException
import clean.android.network.exception.InvalidResponseBodyException
import retrofit2.Response
import java.net.CacheResponse

class ApiErrorParser {
    fun <E> parseError(response: Response<E>): Exception {
        if (response.body() == null) return ApiException()
        return InvalidResponseBodyException()
    }
}