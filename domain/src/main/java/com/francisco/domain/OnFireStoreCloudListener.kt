package com.francisco.domain

import java.lang.Exception

interface OnFireStoreCloudListener {
    fun addOnSuccessListener(state: OnFireStoreCloudResponse)
    fun addOnFailureListener(exception: Exception)
}