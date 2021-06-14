package com.francisco.domain.usercases

import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.OnGetUserInformationResponse
import javax.inject.Inject

class GetUserInformation @Inject constructor(val fireStoreDatabaseRepository: FireStoreDatabaseRepository) {
    fun invoke(
        userId: String,
        onGetUserInformationResponse: OnGetUserInformationResponse
    ) {
        return fireStoreDatabaseRepository.getUserInformation(userId, onGetUserInformationResponse)
    }
}