package com.pcandriod.kusitms_hackathon_c.data.remote.service

import com.pcandriod.kusitms_hackathon_c.data.remote.request.SignInRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.request.WriteCustomerRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponsePost
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponseSignIn
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WriteService {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/articles")
    fun postCustomer(
        @Body writeCustomerRequest: WriteCustomerRequest,
    ): Call<ResponsePost>
}