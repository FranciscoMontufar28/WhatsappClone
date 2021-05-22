package com.francisco.data

import android.app.Activity
import com.francisco.domainx.OnCompleteFireBaseListener
import com.francisco.domainx.OnVerificationFireBaseStateChanged

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
}