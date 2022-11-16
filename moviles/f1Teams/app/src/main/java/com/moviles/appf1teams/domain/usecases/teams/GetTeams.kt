package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository

class GetTeams(private val teamRepository: TeamRepository) {
    suspend fun invoke() = teamRepository.getTeams()

}