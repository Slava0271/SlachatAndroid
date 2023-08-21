package clean.android.network.repository

interface ResponseErrorsRepository : BaseNetworkRepository {
    fun getErrorCode(error: String?): Int

    fun extractError(error: Throwable): String
}