package com.francisco.domain.usercases

import android.content.Context
import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.OnFireBaseStorageListener
import java.io.File
import javax.inject.Inject

class SaveImageInCloudStore @Inject constructor(val repository: FireBaseStorageRepository) {
    suspend fun invoke(
        context: Context,
        file: File,
        onFireBaseStorageListener: OnFireBaseStorageListener
    ) {
        repository.saveImage(context, file, onFireBaseStorageListener)
    }
}