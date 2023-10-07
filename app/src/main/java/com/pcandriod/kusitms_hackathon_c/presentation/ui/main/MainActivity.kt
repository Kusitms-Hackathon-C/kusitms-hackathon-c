package com.pcandriod.kusitms_hackathon_c.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.ActivityMainBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home.HomeFragment
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map.MapFragment
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val manager = supportFragmentManager


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
}

