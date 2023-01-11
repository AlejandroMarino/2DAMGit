package com.moviles.f1app.domain.usecases.teams

import com.moviles.f1app.data.repository.TeamRepository
import javax.inject.Inject

class GetTeamByName @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun invoke(name: String) = teamRepository.getTeamByName(name)
}
