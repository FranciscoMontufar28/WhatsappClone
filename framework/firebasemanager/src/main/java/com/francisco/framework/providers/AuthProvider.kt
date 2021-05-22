package com.francisco.framework.providers

import android.app.Activity
import com.francisco.framework.requestparameters.FireBaseAuthenticationParameters
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

abstract class AuthBaseRequest(var parametersFire: FireBaseAuthenticationParameters) {
    fun sendCodeVerification(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) = buildPhoneAuthOptions(phoneNumber, activity, callbacks).run {
        PhoneAuthProvider.verifyPhoneNumber(this)
    }

    private fun buildPhoneAuthOptions(
        phoneNumber: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ): PhoneAuthOptions =
        PhoneAuthOptions.newBuilder(parametersFire.firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(parametersFire.timeStamp, parametersFire.timeUnit)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()

    fun singInPhone(verificationId: String, code: String): Task<AuthResult> {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code)
        return parametersFire.firebaseAuth.signInWithCredential(credential)
    }

    fun getCurrentUser(): String? {
        return if (parametersFire.firebaseAuth.currentUser != null) {
            parametersFire.firebaseAuth.currentUser.uid
        } else {
            null
        }
    }
}

class AuthProvider(fireBaseAuthenticationParameters: FireBaseAuthenticationParameters) :
    AuthBaseRequest(fireBaseAuthenticationParameters)