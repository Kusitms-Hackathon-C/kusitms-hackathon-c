package com.pcandriod.kusitms_hackathon_c.presentation.ui.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.pcandriod.kusitms_hackathon_c.BuildConfig
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.module.api.GlobalApplication
import com.pcandriod.kusitms_hackathon_c.data.remote.request.SignInRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponseSignIn
import com.pcandriod.kusitms_hackathon_c.data.remote.service.LoginService
import com.pcandriod.kusitms_hackathon_c.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityLoginBinding

    private var name: String = ""
    private var accessToken = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        naverLogin()

    }

    private fun naverLogin() {
        val naverClientId = BuildConfig.NAVER_CLIENT_ID
        val naverClientSecret = BuildConfig.NAVER_CLIENT_SECRET
        val api = ApiModule.getInstance().create(LoginService::class.java)

        binding.run {
            btnNaverLogin.setOnClickListener {
                val oAuthLoginCallback = object : OAuthLoginCallback {
                    override fun onSuccess() {
                        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                            override fun onSuccess(result: NidProfileResponse) {
                                name = result.profile?.name.toString()
                                accessToken = NaverIdLoginSDK.getAccessToken().toString()
                                Log.d(TAG, "네이버 로그인 유저 정보 : $name")
                                Log.d(TAG, "인가 토큰 : $accessToken")
                                Log.d(TAG, "${result.profile}")


                                api.postSignIn(
                                    SignInRequest(accessToken, name)
                                ).enqueue(object: Callback<ResponseSignIn> {
                                    override fun onResponse(
                                        call: Call<ResponseSignIn>,
                                        response: Response<ResponseSignIn>
                                    ) {
                                        Log.d(TAG, "API 성공 ${response.body().toString()}")
                                    }

                                    override fun onFailure(
                                        call: Call<ResponseSignIn>,
                                        t: Throwable
                                    ) {
                                        Log.e(TAG, "API 실패 ${t}")
                                    }
                                })


                            }

                            override fun onError(errorCode: Int, message: String) {
                            }

                            override fun onFailure(httpStatus: Int, message: String) {
                            }
                        })
                    }

                    override fun onError(errorCode: Int, message: String) {
                        val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                        Log.e(TAG, "naverAccessToken : $naverAccessToken")
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                    }
                }
                NaverIdLoginSDK.initialize(this@LoginActivity, naverClientId, naverClientSecret, "우리동네지킴이")
                NaverIdLoginSDK.authenticate(this@LoginActivity, oAuthLoginCallback)
            }
        }
    }
}