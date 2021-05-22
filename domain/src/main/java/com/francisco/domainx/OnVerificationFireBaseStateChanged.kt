package com.francisco.domainx

interface OnVerificationFireBaseStateChanged {
    fun onVerificationCompleted(code: String?)
    fun onVerificationFailed(errorCode: String?)
    fun onCodeSent(verificationId: String?)
}