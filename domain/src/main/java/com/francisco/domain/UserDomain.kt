package com.francisco.domain

data class UserDomain(
    val id: String,
    val nickname: String? = null,
    val phone: String? = null,
    val image: String? = null
)