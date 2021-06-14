package com.francisco.domain.usercases

import com.francisco.domain.FirebaseAuthenticationRepository
import javax.inject.Inject

class GetAuthCurrentUser @Inject constructor(val repository: FirebaseAuthenticationRepository) {
    fun invoke(): String? {
        return repository.getAuthCurrentUser()
    }
}