package com.francisco.usercases

import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.OnCompleteFireBaseListener
import javax.inject.Inject

class SignInWithPhoneNumber @Inject constructor(var repository: AuthenticatePhoneNumberRepository) {

    fun invoke(
        code: String,
        verificationId: String,
        onCompleteFireBaseListener: OnCompleteFireBaseListener
    ) {
        repository.signInWithPhoneNumber(code, verificationId, onCompleteFireBaseListener)
    }
}