package com.francisco.usercases

import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.OnFireBaseStorageListener
import javax.inject.Inject

class GetImageUrl @Inject constructor(val repository: FireBaseStorageRepository) {
    fun invoke(onFireBaseStorageListener: OnFireBaseStorageListener) {
        repository.getImageUri(onFireBaseStorageListener)
    }
}