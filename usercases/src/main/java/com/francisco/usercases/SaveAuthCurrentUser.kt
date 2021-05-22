package com.francisco.usercases

import com.francisco.domain.FireStoreRepository
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.UserDomain
import javax.inject.Inject

class SaveAuthCurrentUser @Inject constructor(val repository: FireStoreRepository) {
    fun invoke(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener) {
        repository.createAuthUser(user, onFireStoreCloudListener)
    }
}