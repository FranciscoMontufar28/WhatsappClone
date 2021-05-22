package com.francisco.whatsapptest.presentation

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.francisco.domain.OnCompleteFireBaseListener
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.OnVerificationFireBaseStateChanged
import com.francisco.domain.UserDomain
import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.FireStoreCloudUserCases
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.SuccessCode
import java.lang.Exception
import javax.inject.Inject

class CodeVerificationViewModel @Inject constructor(
    val authenticationUserCases: AuthenticationUserCases,
    val fireStoreCloudUserCases: FireStoreCloudUserCases
) :
    ViewModel() {
    lateinit var mVerificationId: String

    sealed class CodeVerificationNavigation {
        data class VerificationSuccess(val type: SuccessCode, val code: String = "") :
            CodeVerificationNavigation()

        data class VerificationError(val error: String) : CodeVerificationNavigation()
        object HideLoading : CodeVerificationNavigation()
        object ShowLoading : CodeVerificationNavigation()
    }

    private val _event: MutableLiveData<Event<CodeVerificationNavigation>> =
        MutableLiveData()

    val event: LiveData<Event<CodeVerificationNavigation>> get() = _event

    fun authenticateUserByPhoneNumber(phoneNumber: String, activity: Activity) {
        authenticationUserCases.authenticatePhoneNumber.invoke(
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
        authenticationUserCases.signInWithPhoneNumber.invoke(
            code,
            mVerificationId,
            object : OnCompleteFireBaseListener {
                override fun onCompleteListener(isCompleted: Boolean) {
                    if (isCompleted) {
                        _event.value =
                            Event(
                                CodeVerificationNavigation.VerificationSuccess(
                                    SuccessCode.AUTHENTICATEUSER,
                                    code
                                )
                            )
                    } else {
                        _event.value =
                            Event(CodeVerificationNavigation.VerificationError("The code doesn't match"))
                    }
                }
            })
    }

    fun saveAuthCurrentUser(phoneNumber: String) {
        getAuthCurrentUser()?.let { id ->
            val userDomain = UserDomain(id = id, phone = phoneNumber)
            fireStoreCloudUserCases.saveAuthCurrentUser.invoke(
                userDomain, object : OnFireStoreCloudListener {
                    override fun addOnSuccessListener() {
                        _event.value = Event(CodeVerificationNavigation.HideLoading)
                        _event.value =
                            Event(CodeVerificationNavigation.VerificationSuccess(SuccessCode.SAVEDUSER))
                    }

                    override fun addOnFailureListener(exception: Exception) {
                        _event.value = Event(CodeVerificationNavigation.HideLoading)
                        _event.value =
                            Event(CodeVerificationNavigation.VerificationError("The is an error saving the user information"))
                    }
                }
            )
        }
    }

    private fun getAuthCurrentUser(): String? {
        return authenticationUserCases.getAuthCurrentUser.invoke()
    }
}
