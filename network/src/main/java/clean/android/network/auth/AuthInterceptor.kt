package clean.android.network.auth

import clean.android.network.auth.navigator.GlobalNavigator
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.HTTP_INTERNAL_SERVER_ERROR
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class AuthInterceptor : Interceptor, KoinComponent {

    private val apiTokenProvider: ApiTokenProvider by inject()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val decorateRequest = chain.request().newBuilder()

        decorateRequest.addHeader("Content-Type", "application/json")
        decorateRequest.addHeader("Accept", "application/json")

        if (apiTokenProvider.hasToken()) {
            decorateRequest.addHeader("Authorization", "Bearer ${apiTokenProvider.getToken()}")
        }

        val response = chain.proceed(decorateRequest.build())

        if (response.code == HTTP_UNAUTHORIZED) GlobalNavigator.logout()
        if (response.code == HTTP_INTERNAL_SERVER_ERROR) GlobalNavigator.serverError()

        return response
    }
}

