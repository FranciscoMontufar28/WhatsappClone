package com.francisco.usercases

import com.francisco.domain.FireStoreRepository
import com.francisco.domain.OnFireStoreCloudListener
import javax.inject.Inject

class ValidateIfUserExist @Inject constructor(val fireStoreRepository: FireStoreRepository) {
    fun invoke(
        userId: String,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreRepository.validateIfUserExist(userId, onFireStoreCloudListener)
    }
}