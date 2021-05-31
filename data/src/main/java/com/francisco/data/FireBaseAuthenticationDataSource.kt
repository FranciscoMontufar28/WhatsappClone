package com.francisco.data

import android.app.Activity
import com.francisco.domain.OnCompleteFireBaseListener
import com.francisco.domain.OnVerificationFireBaseStateChanged

interface FireBaseAuthenticationDataSource {
    fun validateUserByPhone(
        phoneNumber: String,
        activity: Activity,
        onVerificationFireBaseStateChanged: OnVerificationFireBaseStateChanged
    )

    fun singInUserByPhone(
        code: String,
        verificationId: String,
        onCompleteFireBaseListener: OnCompleteFireBaseListener
    )

    fun getCurrentUser(): String?

    fun signOut()
}