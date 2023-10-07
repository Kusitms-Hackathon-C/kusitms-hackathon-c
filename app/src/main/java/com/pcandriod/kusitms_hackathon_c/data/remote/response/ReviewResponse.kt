package com.pcandriod.kusitms_hackathon_c.data.remote.response

data class ReviewResponse(
    val content: List<ReivewList>,
    val numberOfElements: Int,
    val hasNext: Boolean
)

data class ReivewList(
    val id: Long,
    val username: String,
    val title: String,
    val content: String
)
