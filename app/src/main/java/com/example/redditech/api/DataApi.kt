package com.example.redditech.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader

data class User (
    @field:Json(name = "name") val name: String,
    @field:Json(name = "snoovatar_img") val avatar: String,
    @field:Json(name = "description") val description : String
)

data class Post(
    @field:Json(name = "kind") val kind: String,
    @field:Json(name = "data") val data: PostData,
)

data class PostData(
    @field:Json(name = "subreddit") val subreddit: String,
    @field:Json(name = "selftext") val selftext: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "ups") val ups: String,
    @field:Json(name = "downs") val downs: String,
    @field:Json(name = "author") val author: String
)

data class PagePostData(
    @field:Json(name = "after") val after: String,
    @field:Json(name = "dist") val dist: String,
    @field:Json(name = "children") val children: List<Post>,
    @field:Json(name = "before") val before: String

)

data class ResponsePost(
    @field:Json(name = "data") val data: PagePostData,
    @field:Json(name = "kind") val kind: String
)
