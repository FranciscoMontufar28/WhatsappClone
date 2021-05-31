package com.francisco.framework.implementations

import com.francisco.data.FireStoreCloudDataSource
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.OnFireStoreCloudResponse
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
                onFireStoreCloudListener.addOnSuccessListener(OnFireStoreCloudResponse.CREATE_USER)
            }.addOnFailureListener {
                onFireStoreCloudListener.addOnFailureListener(it)
            }
    }

    override fun updateAuthUser(
        user: UserDomain,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        userProvider.update(user.toUserFireStore()).addOnSuccessListener {
            onFireStoreCloudListener.addOnSuccessListener(OnFireStoreCloudResponse.USER_UPDATE)
        }.addOnFailureListener {
            onFireStoreCloudListener.addOnFailureListener(it)
        }
    }

    override fun validateIfUserExist(
        id: String,
        onFireStoreCloudListener: OnFireStoreCloudListener
    ) {
        userProvider.getUserInfo(id).get().addOnSuccessListener {
            if (it.exists()) {
                onFireStoreCloudListener.addOnSuccessListener(OnFireStoreCloudResponse.USER_EXIST)
            } else {
                onFireStoreCloudListener.addOnSuccessListener(OnFireStoreCloudResponse.NEW_USER)
            }
        }.addOnFailureListener {
            onFireStoreCloudListener.addOnFailureListener(it)
        }
    }
}