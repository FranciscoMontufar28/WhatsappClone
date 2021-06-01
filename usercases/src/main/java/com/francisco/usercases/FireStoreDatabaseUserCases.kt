package com.francisco.usercases

data class FireStoreDatabaseUserCases(
    var saveAuthCurrentUser: SaveAuthCurrentUser,
    var updateAuthCurrentUser: UpdateAuthCurrentUser,
    var validateIfUserExist: ValidateIfUserExist,
    var getUserInformation: GetUserInformation
)