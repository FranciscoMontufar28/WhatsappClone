package com.francisco.data

import android.content.Context

interface AppPreferencesHelper {
    fun setUserLoginAuthStatus(context: Context, isUserAuth: Boolean)
    fun getUserLoginAuthStatus(context: Context): Boolean
}