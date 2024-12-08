package org.marino.tfgpagao.domain.model

data class User(
    val id: Int = 0,
    val email: String = "",
    val password: String = "",
    val verified: Boolean = true,
    val verificationCode: String = ""
)