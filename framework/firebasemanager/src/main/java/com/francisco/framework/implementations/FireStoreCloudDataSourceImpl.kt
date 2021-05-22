package com.francisco.framework.implementations

import com.francisco.data.FireStoreCloudDataSource
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.UserDomain
import com.francisco.framework.providers.UserProvider
import com.francisco.framework.toUserFireStore
import javax.inject.Inject

class FireStoreCloudDataSourceImpl @Inject constructor(var userProvider: UserProvider) :
    FireStoreCloudDataSource {

    override fun createAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        userProvider.create(user.toUserFireStore())
            .addOnSuccessListener {
                onFireStoreCloudListener.addOnSuccessListener()
            }.addOnFailureListener {
                onFireStoreCloudListener.addOnFailureListener(it)
            }
    }

    override fun updateAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        userProvider.update(user.toUserFireStore()).addOnSuccessListener {
            onFireStoreCloudListener.addOnSuccessListener()
        }.addOnFailureListener {
            onFireStoreCloudListener.addOnFailureListener(it)
        }
    }
}