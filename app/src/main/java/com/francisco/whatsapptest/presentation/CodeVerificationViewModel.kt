package com.francisco.whatsapptest.presentation

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.francisco.domainx.OnCompleteFireBaseListener
import com.francisco.domainx.OnVerificationFireBaseStateChanged
import com.francisco.usercases.AuthenticationUserCases
import com.francisco.whatsapptest.util.Event
import javax.inject.Inject

class CodeVerificationViewModel @Inject constructor(val userCases: AuthenticationUserCases) :
    ViewModel() {
    lateinit var mVerificationId: String

    sealed class CodeVerificationNavigation {
        data class VerificationSuccess(val code: String) : CodeVerificationNavigation()
        data class VerificationError(val error: String) : CodeVerificationNavigation()
        object HideLoading : CodeVerificationNavigation()
        object ShowLoading : CodeVerificationNavigation()
    }

    private val _event: MutableLiveData<Event<CodeVerificationNavigation>> =
        MutableLiveData()

    val event: LiveData<Event<CodeVerificationNavigation>> get() = _event

    fun authenticateUserByPhoneNumber(phoneNumber: String, activity: Activity) {
        userCases.authenticatePhoneNumber.invoke(
            phoneNumber,
            activity,
            object : OnVerificationFireBaseStateChanged {
                override fun onVerificationCompleted(code: String?) {
                    code?.let { signInWithVerificationCode(it) }
                }

                override fun onVerificationFailed(errorCode: String?) {
                    errorCode?.let {
                        _event.value =
                            Event(CodeVerificationNavigation.VerificationError(errorCode))
                    }
                }

                override fun onCodeSent(verificationId: String?) {
                    verificationId?.let { mVerificationId = verificationId }
                }
            })
    }

    fun signInWithVerificationCode(code: String) {
        _event.value = Event(CodeVerificationNavigation.ShowLoading)
        userCases.signInWithPhoneNumber.invoke(
            code,
            mVerificationId,
            object : OnCompleteFireBaseListener {
                override fun onCompleteListener(isCompleted: Boolean) {
                    _event.value = Event(CodeVerificationNavigation.HideLoading)
                    if (isCompleted) {
                        _event.value = Event(CodeVerificationNavigation.VerificationSuccess(code))
                    } else {
                        _event.value =
                            Event(CodeVerificationNavigation.VerificationError("The code doesn't match"))
                    }
                }

            })
    }
}
