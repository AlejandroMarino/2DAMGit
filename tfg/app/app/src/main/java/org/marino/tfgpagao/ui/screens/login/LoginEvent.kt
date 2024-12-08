package org.marino.tfgpagao.ui.screens.login

sealed class LoginEvent {
    object ErrorCatch : LoginEvent()
    class DoLogin(val goGroups: () -> Unit) : LoginEvent()
    class EmailChanged(val newEmail: String) : LoginEvent()
    class PasswordChanged(val newPass: String) : LoginEvent()
}