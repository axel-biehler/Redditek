package com.example.redditech.api

import android.content.Context
import com.example.redditech.MainActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "https://oauth.reddit.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private lateinit var apiService: ApiService

fun getApiService(context: Context, token: String?): ApiService {

    // Initialize ApiService if not initialized yet
    if (!::apiService.isInitialized) {
        val retrofit = Retrofit.Builder()
            .client(okhttpClient(context, token))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    return apiService
}

private fun okhttpClient(context: Context, token: String?): OkHttpClient {
    val interceptor = AuthInterceptor(context)
    interceptor.sessionManager.saveAuthToken(token)
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

interface ApiService {
    @GET("api/v1/me")
    suspend fun getUser(): String
}
