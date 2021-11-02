package com.francisco.data

import android.content.Context
import com.francisco.domain.OnFireBaseStorageListener
import java.io.File

interface FireBaseStorageDataSource {

    suspend fun saveImage(
        context: Context,
        file: File,
        onFireBaseStorageListener: OnFireBaseStorageListener
    )
    fun getImageUri(onFireBaseStorageListener: OnFireBaseStorageListener)
    fun deleteImageByUrl(url: String, onFireBaseStorageListener: OnFireBaseStorageListener)
}