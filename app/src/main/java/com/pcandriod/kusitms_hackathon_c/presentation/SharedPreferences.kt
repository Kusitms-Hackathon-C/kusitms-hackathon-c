package com.pcandriod.kusitms_hackathon_c.presentation

import android.content.Context

object MySharedPreferences {
    private const val PREFERENCES_NAME = "MyAppPreferences"
    private const val KEY_USER_ID = "user_id"

    fun saveUserId(context: Context, userId: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_ID, null)
    }
}