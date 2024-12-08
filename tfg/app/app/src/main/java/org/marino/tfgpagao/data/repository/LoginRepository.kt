package org.marino.tfgpagao.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.marino.tfgpagao.data.local.DataStoreManager
import org.marino.tfgpagao.data.remote.LoginRemoteDataSource
import org.marino.tfgpagao.domain.model.User
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginRemoteDataSource: LoginRemoteDataSource, private val dataStoreManager: DataStoreManager) {
    fun login(user: User): Flow<NetworkResult<User>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = loginRemoteDataSource.login(user)
            if (result is NetworkResult.Success) {
                val token = result.data?.token
                if (token != null) {
                    dataStoreManager.saveAuthToken(token)
                }
            }
            emit(result.map { user })
        }.flowOn(Dispatchers.IO)
    }

    fun register(user: User): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = loginRemoteDataSource.register(user)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}