package com.francisco.data

import android.content.Context
import com.francisco.domain.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(val appPreferencesHelper: AppPreferencesHelper) :
    SharedPreferencesRepository {
    override fun getUserLoginAuthStatus(context: Context): Boolean {
        return appPreferencesHelper.getUserLoginAuthStatus(context)
    }

    override fun setUserLoginAuthStatus(context: Context, isUserAuth: Boolean) {
        appPreferencesHelper.setUserLoginAuthStatus(context, isUserAuth)
    }
}