package com.pcandriod.kusitms_hackathon_c.data.module.api

import android.app.Application

class GlobalApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()

        prefs = PreferenceUtil(applicationContext)
    }
}