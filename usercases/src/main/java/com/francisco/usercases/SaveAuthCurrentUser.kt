package com.francisco.usercases

import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.UserDomain
import javax.inject.Inject

class SaveAuthCurrentUser @Inject constructor(val databaseRepository: FireStoreDatabaseRepository) {
    fun invoke(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener) {
        databaseRepository.createAuthUser(user, onFireStoreCloudListener)
    }
}