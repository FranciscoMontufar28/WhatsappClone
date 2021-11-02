package com.francisco.domain.usercases

import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.OnFireBaseStorageListener
import javax.inject.Inject

class DeleteImageUrlFromStorage@Inject constructor(val fireBaseStorageRepository: FireBaseStorageRepository) {
    fun invoke(
        url: String,
        onFireBaseStorageListener: OnFireBaseStorageListener
    ) {
        fireBaseStorageRepository.deleteImageByUrl(url, onFireBaseStorageListener)
    }
}