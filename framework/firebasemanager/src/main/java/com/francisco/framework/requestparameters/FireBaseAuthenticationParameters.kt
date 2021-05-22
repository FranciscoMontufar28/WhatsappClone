package com.francisco.framework

import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

data class FireBaseAuthenticationParameters(
    var timeStamp: Long,
    var timeUnit: TimeUnit,
    var firebaseAuth: FirebaseAuth
)