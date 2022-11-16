package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository

class GetTeamById(private val teamRepository: TeamRepository) {
    suspend fun invoke(id: Int) = teamRepository.getTeam(id)
}