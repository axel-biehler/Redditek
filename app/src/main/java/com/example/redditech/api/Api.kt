package com.example.redditech.api

import com.squareup.moshi.Json

data class User(val display_name: String) {
    @Json(name = "display_name") val displayName: String = ""
}
