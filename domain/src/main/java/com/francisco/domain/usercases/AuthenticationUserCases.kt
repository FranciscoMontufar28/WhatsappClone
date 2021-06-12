package com.francisco.domain.usercases

data class AuthenticationUserCases(
    var authenticatePhoneNumber: AuthenticatePhoneNumber,
    var signInWithPhoneNumber: SignInWithPhoneNumber,
    var getAuthCurrentUser: GetAuthCurrentUser,
    var signOutUserAuth: SignOutUserAuth
)