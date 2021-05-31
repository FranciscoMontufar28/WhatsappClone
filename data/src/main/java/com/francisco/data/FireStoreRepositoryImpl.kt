package com.francisco.data

import com.francisco.domain.FireStoreRepository
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.UserDomain
import javax.inject.Inject

class FireStoreRepositoryImpl @Inject constructor(var fireStoreCloudDataSource: FireStoreCloudDataSource) :
    FireStoreRepository {

    override fun createAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreCloudDataSource.createAuthUser(user, onFireStoreCloudListener)
    }

    override fun updateAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreCloudDataSource.updateAuthUser(user, onFireStoreCloudListener)
    }

    override fun validateIfUserExist(
        userId: String,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreCloudDataSource.validateIfUserExist(userId, onFireStoreCloudListener)
    }
}