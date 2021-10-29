package com.example.redditech.api

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("name")
    var username: String,
)