package com.francisco.domain.usercases

data class FireStoreDatabaseUserCases(
    var saveAuthCurrentUser: SaveAuthCurrentUser,
    var updateAuthCurrentUser: UpdateAuthCurrentUser,
    var validateIfUserExist: ValidateIfUserExist,
    var getUserInformation: GetUserInformation
)