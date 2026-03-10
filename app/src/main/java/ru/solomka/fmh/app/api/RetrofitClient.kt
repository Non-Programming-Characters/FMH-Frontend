package ru.solomka.fmh.app.api

import ru.solomka.fmh.app.network.HttpProtocol
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(
    private val protocol: HttpProtocol = HttpProtocol.HTTP,
    private val server: String = "localhost",
    private val port: Int = 8080
) {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("${protocol.protocolPrefix}://${server}:${port}")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Api> getApiClient(apiClazz: Class<in T>): T {
        return retrofit.create(apiClazz) as T
    }
}