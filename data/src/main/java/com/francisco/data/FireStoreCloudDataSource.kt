package com.francisco.data

import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.UserDomain

interface FireStoreCloudDataSource {
    fun createAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun updateAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun validateIfUserExist(id: String, onFireStoreCloudListener: OnFireStoreCloudListener)
}