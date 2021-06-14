package com.francisco.domain.usercases

import com.francisco.domain.FirebaseAuthenticationRepository
import javax.inject.Inject

class SignOutUserAuth @Inject constructor(val firebaseAuthenticationRepository: FirebaseAuthenticationRepository) {
    fun invoke(){
        firebaseAuthenticationRepository.signOut()
    }
}