package org.marino.tfgpagao.ui.screens.login

data class LoginState (
    val email: String = "",
    val emailIsValid: Boolean = false,
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
)