package com.francisco.framework.requestparameters

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

data class FireStorageParameters(
    var firebaseStorage: FirebaseStorage = Firebase.storage
)