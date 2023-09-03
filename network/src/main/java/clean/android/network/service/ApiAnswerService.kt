package clean.android.network.service

import clean.android.network.either.Either
import retrofit2.Response

class ApiAnswerService(private val errorParser: ApiErrorParser) {
    fun <T, E> extract(response: Response<E>): Either<T> {
        val body = response.body()
        if (!response.isSuccessful || body == null)
            return Either.failure(errorParser.parseError(response))
        return Either.success(body as T)
    }

    fun <E> extractAnswerWithoutBody(response: Response<E>): Either<Unit> {
        if (!response.isSuccessful) return Either.failure(errorParser.parseError(response))
        return Either.success(Unit)
    }

    fun <T, E> extractBytes(response: Response<E>): Either<T> {
        if (!response.isSuccessful)
            return Either.failure(errorParser.parseError(response))
        return Either.success(response.raw().body.bytes() as T)
    }
}