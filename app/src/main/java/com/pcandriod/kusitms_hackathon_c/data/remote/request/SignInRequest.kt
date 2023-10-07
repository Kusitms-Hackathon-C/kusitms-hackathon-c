package com.pcandriod.kusitms_hackathon_c.data.remote.request

import com.squareup.moshi.Json

data class SignInRequest(
    @field:Json(name = "accessToken")
    val accessToken: String,
    @field:Json(name = "name")
    val name: String
    )