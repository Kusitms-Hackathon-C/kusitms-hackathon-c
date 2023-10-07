package com.pcandriod.kusitms_hackathon_c.data.remote.service

import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponsePostDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PostService {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/article/{articleId}")
    fun getPostDetail(
        @Path("articleId") articleId: Int,
    ): Call<ResponsePostDetail>

}