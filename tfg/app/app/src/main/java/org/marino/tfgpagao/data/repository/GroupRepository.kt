package org.marino.tfgpagao.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.marino.tfgpagao.data.remote.GroupRemoteDataSource
import org.marino.tfgpagao.domain.model.Group
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class GroupRepository @Inject constructor(private val groupRemoteDataSource: GroupRemoteDataSource) {
    fun fetchGroups(): Flow<NetworkResult<List<Group>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = groupRemoteDataSource.fetchGroups()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchGroup(id: Int): Flow<NetworkResult<Group>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = groupRemoteDataSource.fetchGroup(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun addGroup(group: Group): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = groupRemoteDataSource.addGroup(group)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}