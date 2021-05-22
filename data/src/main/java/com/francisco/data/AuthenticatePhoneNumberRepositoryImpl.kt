package com.francisco.data

import android.app.Activity
import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.OnCompleteFireBaseListener
import com.francisco.domain.OnVerificationFireBaseStateChanged
import javax.inject.Inject

class AuthenticatePhoneNumberRepositoryImpl @Inject constructor(var fireBaseAuthenticationDataSource: FireBaseAuthenticationDataSource) :
    AuthenticatePhoneNumberRepository {

    override fun authenticateWithPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        onVerificationFireBaseStateChanged: OnVerificationFireBaseStateChanged
    ) {
        fireBaseAuthenticationDataSource.validateUserByPhone(
            phoneNumber,
            activity,
            onVerificationFireBaseStateChanged
        )
    }

    override fun signInWithPhoneNumber(
        code: String,
        verificationId: String,
        onCompleteFireBaseListener: OnCompleteFireBaseListener
    ) {
        fireBaseAuthenticationDataSource.singInUserByPhone(
            code,
            verificationId,
            onCompleteFireBaseListener
        )
    }

    override fun getAuthCurrentUser(): String? {
        return fireBaseAuthenticationDataSource.getCurrentUser()
    }
}