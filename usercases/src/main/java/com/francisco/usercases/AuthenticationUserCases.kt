package com.francisco.usercases

data class AuthenticationUserCases(
    var authenticatePhoneNumber: AuthenticatePhoneNumber,
    var signInWithPhoneNumber: SignInWithPhoneNumber,
    var getAuthCurrentUser: GetAuthCurrentUser,
    var signOutUserAuth: SignOutUserAuth
)