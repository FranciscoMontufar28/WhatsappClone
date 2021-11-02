package com.francisco.domain

import android.content.Context
import java.io.File

interface FireBaseStorageRepository {

    suspend fun saveImage(
        context: Context, file: File,
        onFireBaseStorageListener: OnFireBaseStorageListener
    )
    fun getImageUri(onFireBaseStorageListener: OnFireBaseStorageListener)
    fun deleteImageByUrl(url: String, onFireBaseStorageListener: OnFireBaseStorageListener)
}