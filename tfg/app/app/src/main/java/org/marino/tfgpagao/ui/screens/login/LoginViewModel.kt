package org.marino.tfgpagao.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.model.User
import org.marino.tfgpagao.domain.usecases.login.Login
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: Login,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.DoLogin -> {
                doLogin(event.goGroups)
            }
            LoginEvent.ErrorCatch -> {
                errorCatch()
            }

            is LoginEvent.EmailChanged -> {
                emailChanged(event.newEmail)
            }

            is LoginEvent.PasswordChanged -> {
                passwordChanged(event.newPass)
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
        _state.update { it.copy(password = newPassword) }
    }

    private fun doLogin(goGroups: () -> Unit) {
        viewModelScope.launch {
            val emailIsValid = _state.value.emailIsValid
            val passwordIsValid = _state.value.password.isNotEmpty()
            if (emailIsValid && passwordIsValid) {
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    val user = User(0, _state.value.email, _state.value.password)
                    login.invoke(user).collect { result ->
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
                                goGroups()
                            }
                            is NetworkResult.SuccessNoData -> {
                                _state.update { it.copy(isLoading = false) }
                                goGroups()
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
