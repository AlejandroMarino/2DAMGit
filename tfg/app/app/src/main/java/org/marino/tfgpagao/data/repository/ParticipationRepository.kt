package org.marino.tfgpagao.data.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.marino.tfgpagao.data.remote.ParticipatioRemoteDataSource
import org.marino.tfgpagao.domain.model.Participation
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class ParticipationRepository @Inject constructor(private val participationRemoteDataSource: ParticipatioRemoteDataSource) {

    fun fetchParticipationsOfReceipt(receiptId: Int): Flow<NetworkResult<List<Participation>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = participationRemoteDataSource.fetchParticipationsOfReceipt(receiptId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchParticipation(memberId: Int, receiptId: Int): Flow<NetworkResult<Participation>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = participationRemoteDataSource.fetchParticipation(memberId, receiptId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun addParticipation(participation: Participation): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = participationRemoteDataSource.addParticipation(participation)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}