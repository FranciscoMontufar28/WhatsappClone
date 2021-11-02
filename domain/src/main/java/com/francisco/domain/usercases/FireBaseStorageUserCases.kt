package com.francisco.domain.usercases

data class FireBaseStorageUserCases(
    val saveImageInCloudStore: SaveImageInCloudStore,
    val getImageUrl: GetImageUrl,
    val deleteImageUrlFromStorage: DeleteImageUrlFromStorage
)