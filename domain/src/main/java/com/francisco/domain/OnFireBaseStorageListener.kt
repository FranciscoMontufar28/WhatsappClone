package com.francisco.domain

import java.lang.Exception

interface OnFireBaseStorageListener {
    fun addOnSuccessListener(url: String)
    fun addOnFailureListener(exception: Exception)
}