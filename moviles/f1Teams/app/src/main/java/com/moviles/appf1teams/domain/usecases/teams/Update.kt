package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository
import javax.inject.Inject

class Update @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun invoke(
        id: Int?,
        name: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ) = id?.let { teamRepository.updateTeam(it, name, performance, tyre, winner) }

}