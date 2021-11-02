package com.francisco.domain.usercases

import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.OnFireStoreCloudListener
import javax.inject.Inject

class DeleteProfileImage @Inject constructor(val fireStoreDatabaseRepository: FireStoreDatabaseRepository) {
    fun invoke(
        userId: String,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreDatabaseRepository.deleteImage(userId, onFireStoreCloudListener)
    }
}