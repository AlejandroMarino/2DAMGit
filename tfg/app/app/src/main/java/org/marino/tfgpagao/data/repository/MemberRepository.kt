package org.marino.tfgpagao.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.marino.tfgpagao.data.remote.MemberRemoteDataSource
import org.marino.tfgpagao.domain.model.Member
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class MemberRepository @Inject constructor(private val memberRemoteDataSource: MemberRemoteDataSource) {

    fun fetchMembersOfGroup(groupId: Int): Flow<NetworkResult<List<Member>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = memberRemoteDataSource.fetchMembersOfGroup(groupId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchMember(id: Int): Flow<NetworkResult<Member>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = memberRemoteDataSource.fetchMember(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getBalanceOfMember(id: Int): Flow<NetworkResult<Double>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = memberRemoteDataSource.getBalanceOfMember(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun addMember(member: Member): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = memberRemoteDataSource.addMember(member)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}