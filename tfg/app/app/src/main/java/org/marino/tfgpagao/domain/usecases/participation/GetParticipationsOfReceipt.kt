package org.marino.tfgpagao.domain.usecases.participation

import org.marino.tfgpagao.data.repository.ParticipationRepository
import org.marino.tfgpagao.data.repository.ReceiptRepository
import javax.inject.Inject

class GetParticipationsOfReceipt @Inject constructor(private val participationRepository: ParticipationRepository) {
    operator fun invoke(receiptId: Int) = participationRepository.fetchParticipationsOfReceipt(receiptId)
}