package com.francisco.domain

interface FireStoreDatabaseRepository {
    fun createAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun updateAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun validateIfUserExist(userId: String, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun getUserInformation(userId: String, onGetUserInformationResponse: OnGetUserInformationResponse)
}