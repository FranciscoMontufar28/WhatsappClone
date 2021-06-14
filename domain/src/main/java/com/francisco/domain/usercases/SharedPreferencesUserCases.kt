package com.francisco.domain.usercases

data class SharedPreferencesUserCases(
    val getUserLoginAuthStatus: GetUserLoginAuthStatus,
    val setUserLoginAuthStatus: SetUserLoginAuthStatus
)