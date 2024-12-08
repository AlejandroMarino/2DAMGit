package org.marino.tfgpagao.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.model.User
import org.marino.tfgpagao.domain.usecases.login.Register
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: Register,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun handleEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.DoRegister -> {
                doRegister(event.goLogin)
            }

            is RegisterEvent.EmailChanged -> {
                emailChanged(event.newEmail)
            }

            RegisterEvent.ErrorCatch -> {
                errorCatch()
            }

            is RegisterEvent.PasswordChanged -> {
                passwordChanged(event.newPass)
            }

            is RegisterEvent.RepeatedPasswordChanged -> {
                repeatedPasswordChanged(event.newRepeatedPass)
            }
        }
    }

    private fun errorCatch() {
        _state.update {
            it.copy(
                error = "",
            )
        }
    }

    private fun emailChanged(newEmail: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        val emailIsValid = newEmail.matches(emailRegex)
        _state.update { it.copy(email = newEmail, emailIsValid = emailIsValid) }
    }

    private fun passwordChanged(newPassword: String) {
        val repeatedPass = _state.value.repeatedPassword
        val coincidentPass = repeatedPass == newPassword
        _state.update { it.copy(password = newPassword, coincidentPasswords = coincidentPass) }
    }

    private fun repeatedPasswordChanged(newRepeatedPassword: String) {
        val pass = _state.value.password
        val coincidentPass = pass == newRepeatedPassword
        _state.update {
            it.copy(
                repeatedPassword = newRepeatedPassword,
                coincidentPasswords = coincidentPass
            )
        }
    }

    private fun doRegister(goLogin: () -> Unit) {
        viewModelScope.launch {
            val emailIsValid = _state.value.emailIsValid
            val passwordIsValid =
                _state.value.coincidentPasswords && _state.value.password.isNotBlank()
            if (emailIsValid && passwordIsValid) {
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    val user = User(0, _state.value.email, _state.value.password)
                    register.invoke(user).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "",
                                        isLoading = false
                                    )
                                }
                            }

                            is NetworkResult.Loading -> _state.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> {
                                _state.update { it.copy(isLoading = false) }
                                goLogin()
                            }

                            is NetworkResult.SuccessNoData -> {
                                _state.update { it.copy(isLoading = false) }
                                goLogin()
                            }
                        }
                    }
                } else {
                    _state.update { it.copy(error = "Internet connection needed") }
                }
            } else {
                if (!emailIsValid) {
                    _state.update { it.copy(error = "Invalid email") }
                } else {
                    _state.update { it.copy(error = "Invalid password") }
                }
            }
        }
    }
}