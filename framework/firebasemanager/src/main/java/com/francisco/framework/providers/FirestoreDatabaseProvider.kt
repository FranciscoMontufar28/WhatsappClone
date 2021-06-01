package com.francisco.framework.providers

import com.francisco.framework.UserFireStore
import com.francisco.framework.requestparameters.FireStoreCloudParameters
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

abstract class UserBaseRequest(val parameters: FireStoreCloudParameters) {

    fun create(user: UserFireStore): Task<Void> {
        return parameters.collection.document(user.id).set(user)
    }

    fun update(user: UserFireStore): Task<Void> {
        val map = hashMapOf<String, Any?>()
        user.nickname?.let { map["nickname"] = it }
        user.phone?.let { map["phone"] = it }
        user.image?.let { map["image"] = it }
        return parameters.collection.document(user.id).update(map)
    }

    fun getUserInfo(id: String): DocumentReference {
        return parameters.collection.document(id)
    }
}

class UserProvider(fireStoreCloudParameters: FireStoreCloudParameters) :
    UserBaseRequest(fireStoreCloudParameters)