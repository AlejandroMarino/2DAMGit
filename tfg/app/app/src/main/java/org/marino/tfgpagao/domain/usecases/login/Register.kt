package org.marino.tfgpagao.domain.usecases.login

import org.marino.tfgpagao.data.repository.LoginRepository
import org.marino.tfgpagao.domain.model.User
import javax.inject.Inject

class Register @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(user: User) = loginRepository.register(user)
}