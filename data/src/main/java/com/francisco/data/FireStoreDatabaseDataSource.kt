package com.francisco.data

import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.OnGetUserInformationResponse
import com.francisco.domain.UserDomain

interface FireStoreDatabaseDataSource {
    fun createAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun updateAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun validateIfUserExist(id: String, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun getUserInformation(
        userId: String,
        onGetUserInformationResponse: OnGetUserInformationResponse
    )
    fun deleteImage(id: String, onFireStoreCloudListener: OnFireStoreCloudListener)
}