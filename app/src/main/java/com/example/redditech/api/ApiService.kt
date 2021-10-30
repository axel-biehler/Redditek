package com.example.redditech.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET(Constants.PROFILE)
    fun userInfo(): Call<User>

    @GET
    fun getAvatar(@Url url: String?): Call<ResponseBody>
}