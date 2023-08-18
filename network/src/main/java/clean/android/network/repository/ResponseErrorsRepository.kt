package clean.android.network.repository

import clean.android.network.entity.BaseEntity

interface ResponseErrorsRepository : BaseNetworkRepository {
    fun getErrorCode(error: String?): Int

    fun extractError(error: Throwable): String
}