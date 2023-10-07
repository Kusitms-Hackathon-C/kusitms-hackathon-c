package com.pcandriod.kusitms_hackathon_c.data.remote.response


import com.squareup.moshi.Json

data class ResponsePost(
    @Json(name = "content")
    val content: List<Content>,
    @Json(name = "hasNext")
    val hasNext: Boolean,
    @Json(name = "numberOfElements")
    val numberOfElements: Int
) {
    data class Content(
        @Json(name = "content")
        val content: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "title")
        val title: String
    )
}