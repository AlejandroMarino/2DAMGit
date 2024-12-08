package org.marino.tfgpagao.domain.usecases.login

import org.marino.tfgpagao.data.repository.LoginRepository
import org.marino.tfgpagao.domain.model.User
import javax.inject.Inject

class Login @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(user: User) = loginRepository.login(user)
}