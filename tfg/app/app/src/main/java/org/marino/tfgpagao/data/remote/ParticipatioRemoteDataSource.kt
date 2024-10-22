package org.marino.tfgpagao.data.remote

import org.marino.tfgpagao.domain.model.Participation
import org.marino.tfgpagao.network.services.ParticipationService
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class ParticipatioRemoteDataSource @Inject constructor(private val participationService: ParticipationService) :
    BaseApiResponse() {

    suspend fun fetchParticipationsOfReceipt(receiptID: Int): NetworkResult<List<Participation>> {
        return safeApiCall(apiCall = { participationService.getParticipationsOfReceipt(receiptID) })
    }

    suspend fun fetchParticipation(memberId: Int, receiptID: Int): NetworkResult<Participation> {
        return safeApiCall(apiCall = { participationService.getParticipation(memberId, receiptID) })
    }

    suspend fun addParticipation(participation: Participation): NetworkResult<Void> {
        return safeApiCall(apiCall = { participationService.add(participation) })
    }
}