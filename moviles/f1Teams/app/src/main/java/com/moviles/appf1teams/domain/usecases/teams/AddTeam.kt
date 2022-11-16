package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository
import com.moviles.appf1teams.domain.modelo.Team

class AddTeam(private val teamRepository: TeamRepository) {

    suspend fun invoke(team: Team): Boolean {
        return try {
            teamRepository.addTeam(team)
            true
        }catch (e: Exception) {
            false
        }
    }
}