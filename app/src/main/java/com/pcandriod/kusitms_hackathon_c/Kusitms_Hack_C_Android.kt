package com.pcandriod.kusitms_hackathon_c

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class Kusitms_Hack_C_Android : Application() {
    companion object {
        lateinit var mSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mSharedPreferences = applicationContext.getSharedPreferences("Kusitms_Hack_C_Android", MODE_PRIVATE)
    }
}