package com.francisco.usercases

import com.francisco.domain.AuthenticatePhoneNumberRepository
import javax.inject.Inject

class GetAuthCurrentUser @Inject constructor(val repository: AuthenticatePhoneNumberRepository) {
    fun invoke(): String? {
        return repository.getAuthCurrentUser()
    }
}