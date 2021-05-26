package com.francisco.whatsapptest.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.francisco.usercases.SharedPreferencesUserCases
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(val sharedPreferencesUserCases: SharedPreferencesUserCases) :
    ViewModel() {

    private val _event: MutableLiveData<Boolean> =
        MutableLiveData()

    val event: LiveData<Boolean> get() = _event

    fun getUserLoginAuthStatus(context: Context) {
        _event.value = sharedPreferencesUserCases.getUserLoginAuthStatus.invoke(context)
    }
}