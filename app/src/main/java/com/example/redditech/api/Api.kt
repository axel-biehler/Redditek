package com.example.redditech.api

import com.squareup.moshi.Json

data class User(
    @Json(name = "name") val name: String
)
