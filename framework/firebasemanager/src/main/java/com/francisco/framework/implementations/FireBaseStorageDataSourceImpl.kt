package com.francisco.framework.implementations

import android.content.Context
import com.francisco.data.FireBaseStorageDataSource
import com.francisco.domain.OnFireBaseStorageListener
import com.francisco.framework.providers.ImageProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import javax.inject.Inject

class FireBaseStorageDataSourceImpl @Inject constructor(val imageProvider: ImageProvider) :
    FireBaseStorageDataSource {

    override suspend fun saveImage(
        context: Context,
        file: File,
        onFireBaseStorageListener: OnFireBaseStorageListener
    ) {
        imageProvider.saveImage(context, file).addOnSuccessListener {
            onFireBaseStorageListener.addOnSuccessListener("")
        }.addOnFailureListener {
            onFireBaseStorageListener.addOnFailureListener(it)
        }
    }

    override fun getImageUri(onFireBaseStorageListener: OnFireBaseStorageListener) {
        imageProvider.getImageUri().addOnSuccessListener {
            onFireBaseStorageListener.addOnSuccessListener(it.toString())
        }.addOnFailureListener {
            onFireBaseStorageListener.addOnFailureListener(it)
        }
    }
}