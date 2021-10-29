package com.example.redditech.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET(Constants.PROFILE)
    fun fetchPosts(): Call<User>
}