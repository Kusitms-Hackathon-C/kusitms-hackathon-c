package com.pcandriod.kusitms_hackathon_c.presentation.ui.main

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.pcandriod.kusitms_hackathon_c.BuildConfig
import androidx.fragment.app.Fragment
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.data.ResultSearchKeyword
import com.pcandriod.kusitms_hackathon_c.data.remote.service.KakaoAPI
import com.pcandriod.kusitms_hackathon_c.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home.HomeFragment
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map.MapFragment
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map.StoreInfoFragment
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val manager = supportFragmentManager

    var storeId = 0L
    var storeName = ""


    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK ${BuildConfig.KAKAO_SEARCH_KEY}" // REST API 키
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        HomeFragment().changeFragment()
        initBottomNav()
    }

    private fun initBottomNav() {
        binding.btmNavMain.itemIconTintList = null
        binding.btmNavMain.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_fragment_home -> {
                    HomeFragment().changeFragment()
                }
                R.id.nav_fragment_map -> {
                    MapFragment().changeFragment()
                }
                R.id.nav_fragment_mypage -> {
                    MyPageFragment().changeFragment()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun Fragment.changeFragment() {
        manager.beginTransaction().replace(R.id.fv_main, this).commit()
    }

    // 키보드 내리는 메서드
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

