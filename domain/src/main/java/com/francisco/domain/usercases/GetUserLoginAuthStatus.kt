package com.francisco.domain.usercases

import android.content.Context
import com.francisco.domain.SharedPreferencesRepository
import javax.inject.Inject

class GetUserLoginAuthStatus @Inject constructor(val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun invoke(context: Context): Boolean {
        return sharedPreferencesRepository.getUserLoginAuthStatus(context)
    }
}