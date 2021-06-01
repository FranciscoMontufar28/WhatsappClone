package com.francisco.domain

import java.lang.Exception

interface OnGetUserInformationResponse {
    fun addOnSuccessListener(userDomain: UserDomain)
    fun addOnFailureListener(exception: Exception)
}