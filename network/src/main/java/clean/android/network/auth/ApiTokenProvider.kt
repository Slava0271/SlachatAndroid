package clean.android.network.auth

interface ApiTokenProvider {
    fun saveToken(accessToken: String, refreshToken: String)

    fun saveAuthState(state: Boolean)

    fun getAuthState(): Boolean

    fun getToken(): String

    fun getRefreshToken(): String

    fun hasToken(): Boolean

    fun hasRefreshToken(): Boolean

    fun getTokenTime(): Long

    fun clear()

    fun saveTermsStatus(status: Boolean)

    fun getTermsStatus(): Boolean
}