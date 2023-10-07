package com.pcandriod.kusitms_hackathon_c.data.remote.response


import com.squareup.moshi.Json

data class ResponseSignIn(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "role")
    val role: String
)