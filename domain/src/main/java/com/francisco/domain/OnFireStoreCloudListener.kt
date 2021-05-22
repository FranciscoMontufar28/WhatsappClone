package com.francisco.domain

import java.lang.Exception

interface OnFireStoreCloudListener {
    fun addOnSuccessListener()
    fun addOnFailureListener(exception: Exception)
}