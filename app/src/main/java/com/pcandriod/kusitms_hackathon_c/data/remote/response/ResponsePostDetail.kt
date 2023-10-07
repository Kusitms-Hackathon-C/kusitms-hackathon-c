package com.pcandriod.kusitms_hackathon_c.data.remote.response


import com.squareup.moshi.Json

data class ResponsePostDetail(
    @Json(name = "category")
    val category: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "username")
    val username: String
)