package com.francisco.usercases

import com.francisco.domain.AuthenticatePhoneNumberRepository
import javax.inject.Inject

class SignOutUserAuth @Inject constructor(val authenticatePhoneNumberRepository: AuthenticatePhoneNumberRepository) {
    fun invoke(){
        authenticatePhoneNumberRepository.signOut()
    }
}