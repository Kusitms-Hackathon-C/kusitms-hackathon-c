package com.pcandriod.kusitms_hackathon_c.data.remote.response

data class StoreSosResponse(
    val content: List<SosList>,
    val numberOfElements: Int,
    val hasNext: Boolean
)

data class SosList(
    val id: Long,
    val title: String,
    val content: String
)
