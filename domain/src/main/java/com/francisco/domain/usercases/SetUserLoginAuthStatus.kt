package com.francisco.domain.usercases

import android.content.Context
import com.francisco.domain.SharedPreferencesRepository
import javax.inject.Inject

class SetUserLoginAuthStatus @Inject constructor(val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun invoke(context: Context, isUserAuth: Boolean) {
        sharedPreferencesRepository.setUserLoginAuthStatus(context, isUserAuth)
    }
}