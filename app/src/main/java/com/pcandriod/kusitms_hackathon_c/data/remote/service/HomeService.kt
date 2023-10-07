package com.pcandriod.kusitms_hackathon_c.data.remote.service

import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponsePost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface HomeService {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/api/articles/category?search=가게 소식")
    fun getPostCategory(
    ): Call<ResponsePost>
}