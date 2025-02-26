package com.gsbina.android.githubusers.data

import com.gsbina.android.githubusers.BuildConfig
import com.gsbina.android.githubusers.data.users.GitHubService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object ApiModule {
    private const val BASE_URL = "https://api.github.com/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val logger = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val gitHubService: GitHubService = retrofit.create()
}