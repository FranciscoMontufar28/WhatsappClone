package com.francisco.domain

interface FireStoreRepository {
    fun createAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
    fun updateAuthUser(user: UserDomain, onFireStoreCloudListener: OnFireStoreCloudListener)
}