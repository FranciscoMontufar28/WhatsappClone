package com.francisco.framework

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

abstract class BaseRequest(var parameters: BaseRequestParameters) {
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
        PhoneAuthOptions.newBuilder(parameters.firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(parameters.timeStamp, parameters.timeUnit)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()

    fun singInPhone(verificationId: String, code: String): Task<AuthResult> {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code)
        return parameters.firebaseAuth.signInWithCredential(credential)
    }
}

class AuthProvider(baseRequestParameters: BaseRequestParameters) :
    BaseRequest(baseRequestParameters)