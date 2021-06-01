package com.francisco.usercases

import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.OnFireStoreCloudListener
import javax.inject.Inject

class ValidateIfUserExist @Inject constructor(val fireStoreDatabaseRepository: FireStoreDatabaseRepository) {
    fun invoke(
        userId: String,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreDatabaseRepository.validateIfUserExist(userId, onFireStoreCloudListener)
    }
}