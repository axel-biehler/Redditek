package com.example.redditech.api

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class User (
    @SerializedName("name")
    var username: String,

    @SerializedName("snoovatar_img")
    var avatar: String,

    @SerializedName("description")
    var description: String
)