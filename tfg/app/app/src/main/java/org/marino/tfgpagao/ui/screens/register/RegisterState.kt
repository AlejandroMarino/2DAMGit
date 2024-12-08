package org.marino.tfgpagao.ui.screens.register

data class RegisterState(
    val email: String = "",
    val emailIsValid: Boolean = false,
    val password: String = "",
    val repeatedPassword: String = "",
    val coincidentPasswords: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)