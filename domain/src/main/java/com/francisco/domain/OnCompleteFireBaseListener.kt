package com.francisco.domain

interface OnCompleteFireBaseListener {
    fun onSuccessCompleteListener(id: String)
    fun onFailCompleteListener()
    fun onSuccessCompleteNullUserListener()
}