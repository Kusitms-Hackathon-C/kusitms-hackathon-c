package com.pcandriod.kusitms_hackathon_c.presentation.ui.main

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.pcandriod.kusitms_hackathon_c.BuildConfig
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.data.ResultSearchKeyword
import com.pcandriod.kusitms_hackathon_c.data.remote.service.KakaoAPI
import com.pcandriod.kusitms_hackathon_c.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK ${BuildConfig.KAKAO_SEARCH_KEY}" // REST API 키
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    // 키보드 내리는 메서드
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

