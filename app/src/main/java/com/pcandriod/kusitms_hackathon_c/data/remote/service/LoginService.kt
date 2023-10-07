package com.pcandriod.kusitms_hackathon_c.data.remote.service

import com.pcandriod.kusitms_hackathon_c.data.remote.request.SignInRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponseSignIn
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface LoginService {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/members/login")
    fun postSignIn(
        @Body signInRequest: SignInRequest,
    ): Call<ResponseSignIn>
}