package com.francisco.framework

import com.francisco.domain.UserDomain

data class UserFireStore(
    val id: String,
    val nickname: String?,
    val phone: String?,
    val image: String?
)

fun UserDomain.toUserFireStore(): UserFireStore = UserFireStore(
    id = this.id,
    nickname = this.nickname,
    phone = this.phone,
    image = this.image
)
