package org.marino.tfgpagao.domain.usecases.participation

import org.marino.tfgpagao.data.repository.ParticipationRepository
import javax.inject.Inject

class GetParticipation @Inject constructor(private val participationRepository: ParticipationRepository) {
    operator fun invoke(memberId: Int, receiptId: Int) = participationRepository.fetchParticipation(memberId, receiptId)
}