package com.pcandriod.kusitms_hackathon_c.data.remote.request

import com.squareup.moshi.Json

data class WriteCustomerRequest(
    @field:Json(name = "accessToken")
    val accessToken: String,
    @field:Json(name = "category")
    val category: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "content")
    val content: String,
)