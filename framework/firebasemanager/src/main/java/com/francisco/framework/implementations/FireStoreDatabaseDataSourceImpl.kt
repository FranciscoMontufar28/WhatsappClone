package com.francisco.framework.implementations

import com.francisco.data.FireStoreDatabaseDataSource
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.OnFireStoreCloudResponse
import com.francisco.domain.OnGetUserInformationResponse
import com.francisco.domain.UserDomain
import com.francisco.framework.UserFireStore
import com.francisco.framework.providers.UserProvider
import com.francisco.framework.toUserDomain
import com.francisco.framework.toUserFireStore
import javax.inject.Inject

class FireStoreDatabaseDataSourceImpl @Inject constructor(var userProvider: UserProvider) :
    FireStoreDatabaseDataSource {

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

    override fun getUserInformation(
        userId: String,
        onGetUserInformationResponse: OnGetUserInformationResponse
    ) {
        userProvider.getUserInfo(userId).get()
            .addOnSuccessListener {
                val userEntity = UserFireStore(
                    id = it.id,
                    nickname = it.getString("nickname"),
                    image = it.getString("image"),
                    phone = it.getString("phone"),
                    about = it.getString("about")
                )
                onGetUserInformationResponse.addOnSuccessListener(userEntity.toUserDomain())
            }
            .addOnFailureListener { onGetUserInformationResponse.addOnFailureListener(it) }
    }

    override fun deleteImage(id: String, onFireStoreCloudListener: OnFireStoreCloudListener) {
        userProvider.deleteImage(id).addOnSuccessListener {
            onFireStoreCloudListener.addOnSuccessListener(OnFireStoreCloudResponse.IMAGE_DELETED)
        }.addOnFailureListener {
            onFireStoreCloudListener.addOnFailureListener(it)
        }
    }
}