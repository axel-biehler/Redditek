package com.example.redditech.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Url

interface ApiService {
    @GET(Constants.PROFILE)
    fun userInfo(): Call<User>

    @GET
    fun getBestPublicationList(@Url url: String?): Call<ResponsePost>

    @GET
    fun getSubredditInfo(@Url url: String?): Call<Subreddit>

    @GET(Constants.SETTINGS)
    fun userSettings(): Call<GetterSettings>
}