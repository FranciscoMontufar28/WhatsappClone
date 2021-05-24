package com.francisco.framework.providers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.francisco.framework.requestparameters.FireStorageParameters
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import java.io.File
import java.util.*

class ImageProvider(fireStorageParameters: FireStorageParameters) :
    StorageRequest(fireStorageParameters)

abstract class StorageRequest(var fireStorageParameters: FireStorageParameters) {

    var reference = fireStorageParameters.firebaseStorage.reference

    suspend fun saveImage(context: Context, file: File): UploadTask {
        val compressedImageFile = Compressor.compress(context, file) {
            resolution(500, 500)
            quality(80)
            format(Bitmap.CompressFormat.JPEG)
            size(2_097_152) // 2 MB
        }
        val storage = reference.child("${Date()}.jpg")
        reference = storage
        return storage.putBytes(compressedImageFile.readBytes())
    }

    fun getImageUri(): Task<Uri> {
        return reference.downloadUrl
    }
}
