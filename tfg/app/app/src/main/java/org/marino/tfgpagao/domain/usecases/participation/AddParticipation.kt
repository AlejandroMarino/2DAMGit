package org.marino.tfgpagao.domain.usecases.participation

import org.marino.tfgpagao.data.repository.ParticipationRepository
import org.marino.tfgpagao.domain.model.Participation
import javax.inject.Inject

class AddParticipation @Inject constructor(private val participationRepository: ParticipationRepository) {
    operator fun invoke(participation: Participation) = participationRepository.addParticipation(participation)
}