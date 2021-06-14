package com.francisco.domain

import android.content.Context

interface SharedPreferencesRepository {
    fun getUserLoginAuthStatus(context: Context): Boolean
    fun setUserLoginAuthStatus(context: Context, isUserAuth: Boolean)
}