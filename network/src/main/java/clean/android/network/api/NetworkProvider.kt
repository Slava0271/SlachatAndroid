package clean.android.network.api

import clean.android.network.auth.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkProvider : KoinComponent {
    private const val OKHTTP_READ_TIMEOUT: Long = 10
    private const val OKHTTP_CONNECT_TIMEOUT = 10L


    fun <T> provideApiService(
        gson: Gson,
        baseUrl: String,
        service: Class<T>
    ): T {
        return provideRetrofitBuilder(gson)
            .baseUrl(baseUrl).build().create(service)
    }

    private fun provideRetrofitBuilder(
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideOkHttp())
    }

    private fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(provideLoggingInterceptor())
            .followRedirects(true)
            .followSslRedirects(true)
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }


    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    private fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
                // HttpLoggingInterceptor.Level.NONE
            )
    }

}
