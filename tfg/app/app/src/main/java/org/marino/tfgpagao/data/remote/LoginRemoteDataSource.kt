package org.marino.tfgpagao.data.remote

import org.marino.tfgpagao.domain.model.User
import org.marino.tfgpagao.network.services.LoginService
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(private val loginService: LoginService) :
    BaseApiResponse() {

    suspend fun login(user: User): NetworkResult<ResponseWithToken<User>> {
        return safeApiCallWithToken(apiCall = { loginService.doLogin(user.email, user.password) })
    }

    suspend fun register(user: User): NetworkResult<Void> {
        return safeApiCall(apiCall = { loginService.register(user.email, user.password) })
    }
}