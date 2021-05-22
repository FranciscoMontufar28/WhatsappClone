package com.francisco.domain

import android.app.Activity

interface AuthenticatePhoneNumberRepository {
    fun authenticateWithPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        onVerificationFireBaseStateChanged: OnVerificationFireBaseStateChanged
    )

    fun signInWithPhoneNumber(
        code: String,
        verificationId: String,
        onCompleteFireBaseListener: OnCompleteFireBaseListener
    )

    fun getAuthCurrentUser(): String?
}