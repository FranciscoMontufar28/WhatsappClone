package com.francisco.data

import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.OnGetUserInformationResponse
import com.francisco.domain.UserDomain
import javax.inject.Inject

class FireStoreDatabaseDatabaseRepositoryImpl @Inject constructor(var fireStoreDatabaseDataSource: FireStoreDatabaseDataSource) :
    FireStoreDatabaseRepository {

    override fun createAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreDatabaseDataSource.createAuthUser(user, onFireStoreCloudListener)
    }

    override fun updateAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreDatabaseDataSource.updateAuthUser(user, onFireStoreCloudListener)
    }

    override fun validateIfUserExist(
        userId: String,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        fireStoreDatabaseDataSource.validateIfUserExist(userId, onFireStoreCloudListener)
    }

    override fun getUserInformation(
        userId: String,
        onGetUserInformationResponse: OnGetUserInformationResponse
    ) {
        return fireStoreDatabaseDataSource.getUserInformation(userId, onGetUserInformationResponse)
    }
}