package com.francisco.usercases

import android.app.Activity
import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.OnVerificationFireBaseStateChanged
import javax.inject.Inject

class AuthenticatePhoneNumber @Inject constructor(var repository: AuthenticatePhoneNumberRepository) {

    fun invoke(
        phoneNumber: String,
        activity: Activity,
        onVerificationFireBaseStateChanged: OnVerificationFireBaseStateChanged
    ) {
        repository.authenticateWithPhoneNumber(
            phoneNumber,
            activity,
            onVerificationFireBaseStateChanged
        )
    }
}