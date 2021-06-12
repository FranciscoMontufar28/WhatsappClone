package com.francisco.domain.usercases

import com.francisco.domain.FirebaseAuthenticationRepository
import com.francisco.domain.OnCompleteFireBaseListener
import javax.inject.Inject

class SignInWithPhoneNumber @Inject constructor(var repository: FirebaseAuthenticationRepository) {

    fun invoke(
        code: String,
        verificationId: String,
        onCompleteFireBaseListener: OnCompleteFireBaseListener
    ) {
        repository.signInWithPhoneNumber(code, verificationId, onCompleteFireBaseListener)
    }
}