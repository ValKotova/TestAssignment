package com.valkotova.testassignment.network

import android.content.Context
import android.util.Log
import com.valkotova.testassignment.BuildConfig
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.math.min

@Module
class NetworkModule {
    @Provides
    fun provideJson(): Json =
        Json(Json) {
            ignoreUnknownKeys = true
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            val interceptor =
                HttpLoggingInterceptor { message -> logLong("network", message, Log::w) }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(interceptor)
        }
        okHttpBuilder.hostnameVerifier { _, _ -> true }
        return okHttpBuilder
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): API =
        retrofit.create(API::class.java)

    private fun logLong(tag: String, text: String, logFunction: (String, String) -> Any, part: Int? = null) {
        val textLength = text.length
        if (textLength > 100_000) {
            val textToPrint = text.substring(0, min(255, text.length))
            val message = "<very long text. L=$textLength> $textToPrint ..."
            logFunction.invoke(tag, message)
            return
        }

        val lineLength = 950
        val textToPrint = text.substring(0, min(lineLength, text.length))
        val message = part?.let { "p$it  $textToPrint" } ?: textToPrint

        logFunction.invoke(tag, message)
        if (textToPrint.length < text.length) {
            val clippedString = text.substring(lineLength)
            logLong(tag, clippedString, logFunction, part?.let { it + 1 } ?: 2)
        }
    }
}