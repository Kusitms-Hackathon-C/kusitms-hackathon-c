package com.pcandriod.kusitms_hackathon_c.data.remote.service

import com.pcandriod.kusitms_hackathon_c.data.remote.request.SignInRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.response.MapStoreResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponseSignIn
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ReviewResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.response.StoreSosResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {
    @GET("/api/stores")
    fun getStoreMapList(): Call<List<MapStoreResponse>>

    @GET("/api/stores/{storeId}/sos")
    fun getStoreSos(@Path("storeId") storeId: Long): Call<StoreSosResponse>

    @GET("/api/reviews/{storeId}/reviews")
    fun getStoreReview(@Path("storeId") storeId: Long): Call<ReviewResponse>
}