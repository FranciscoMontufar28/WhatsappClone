package com.francisco.data

import android.content.Context
import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.OnFireBaseStorageListener
import java.io.File
import javax.inject.Inject

class FireBaseStorageRepositoryImpl @Inject constructor(val fireBaseStorageDataSource: FireBaseStorageDataSource) :
    FireBaseStorageRepository {
    override suspend fun saveImage(
        context: Context,
        file: File,
        onFireBaseStorageListener: OnFireBaseStorageListener
    ) {
        fireBaseStorageDataSource.saveImage(context, file, onFireBaseStorageListener)
    }

    override fun getImageUri(onFireBaseStorageListener: OnFireBaseStorageListener) {
        fireBaseStorageDataSource.getImageUri(onFireBaseStorageListener)
    }

    override fun deleteImageByUrl(url: String, onFireBaseStorageListener: OnFireBaseStorageListener){
        fireBaseStorageDataSource.deleteImageByUrl(url, onFireBaseStorageListener)
    }

}