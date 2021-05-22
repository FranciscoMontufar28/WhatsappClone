package com.francisco.domain

interface OnVerificationFireBaseStateChanged {
    fun onVerificationCompleted(code: String?)
    fun onVerificationFailed(errorCode: String?)
    fun onCodeSent(verificationId: String?)
}