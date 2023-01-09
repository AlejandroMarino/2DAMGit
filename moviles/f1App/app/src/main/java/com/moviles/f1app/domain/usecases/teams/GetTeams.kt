package com.moviles.f1app.domain.usecases.teams

import com.moviles.f1app.data.repository.TeamRepository
import javax.inject.Inject

class GetTeams @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun invoke() = teamRepository.getTeams()
}