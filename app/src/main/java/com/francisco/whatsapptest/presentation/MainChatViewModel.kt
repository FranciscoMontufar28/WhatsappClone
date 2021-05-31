package com.francisco.whatsapptest.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.SharedPreferencesUserCases
import javax.inject.Inject

class MainChatViewModel @Inject constructor(
    val authenticationUserCases: AuthenticationUserCases,
    val sharedPreferencesUserCases: SharedPreferencesUserCases
) :
    ViewModel() {

    fun signOut() {
        authenticationUserCases.signOutUserAuth.invoke()
    }

    fun updateUserLoginAuth(context: Context, isAuth: Boolean){
        sharedPreferencesUserCases.setUserLoginAuthStatus.invoke(context, isAuth)
    }
}