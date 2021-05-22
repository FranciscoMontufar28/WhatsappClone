package com.francisco.framework

import android.app.Activity
import com.francisco.data.FireBaseAuthenticationDataSource
import com.francisco.domain.OnCompleteFireBaseListener
import com.francisco.domain.OnVerificationFireBaseStateChanged
import com.francisco.framework.providers.AuthProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class FireBaseAuthenticationDataSourceImpl @Inject constructor(
    var authProvider: AuthProvider
) : FireBaseAuthenticationDataSource {

    lateinit var mVerificationId: String
    override fun validateUserByPhone(
        phoneNumber: String,
        activity: Activity,
        onVerificationFireBaseStateChanged: OnVerificationFireBaseStateChanged
    ) {
        authProvider.sendCodeVerification(
            phoneNumber,
            activity,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    val code = phoneAuthCredential.smsCode
                    onVerificationFireBaseStateChanged.onVerificationCompleted(code)
                }

                override fun onVerificationFailed(firebaseException: FirebaseException) {
                    val errorMessage = firebaseException.localizedMessage
                    onVerificationFireBaseStateChanged.onVerificationFailed(errorMessage)
                }

                override fun onCodeSent(
                    verficationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verficationId, forceResendingToken)
                    onVerificationFireBaseStateChanged.onCodeSent(verficationId)
                    mVerificationId = verficationId
                }
            })
    }

    override fun singInUserByPhone(
        code: String,
        verificationId: String,
        onCompleteFireBaseListener: OnCompleteFireBaseListener
    ) {
        authProvider.singInPhone(mVerificationId, code).addOnCompleteListener {
            val isSuccess = it.isSuccessful
            onCompleteFireBaseListener.onCompleteListener(isSuccess)
        }
    }

    override fun getCurrentUser(): String? {
        return authProvider.getCurrentUser()
    }
}