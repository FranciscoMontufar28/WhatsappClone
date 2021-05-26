package com.francisco.sharedpreferences

import android.content.Context
import com.francisco.data.AppPreferencesHelper

class AppPreferencesHelperImpl : AppPreferencesHelper {

    override fun setUserLoginAuthStatus(context: Context, isUserAuth: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(WHATSAPP_USER_AUTH_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean(AUTH_USER, isUserAuth)
        }.apply()
    }

    override fun getUserLoginAuthStatus(context: Context): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(WHATSAPP_USER_AUTH_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(AUTH_USER, false)
    }
}