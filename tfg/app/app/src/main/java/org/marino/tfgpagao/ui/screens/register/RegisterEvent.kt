package org.marino.tfgpagao.ui.screens.register

sealed class RegisterEvent {
    object ErrorCatch : RegisterEvent()
    class DoRegister(val goLogin: () -> Unit) : RegisterEvent()
    class EmailChanged(val newEmail: String) : RegisterEvent()
    class PasswordChanged(val newPass: String) : RegisterEvent()
    class RepeatedPasswordChanged(val newRepeatedPass: String) : RegisterEvent()
}