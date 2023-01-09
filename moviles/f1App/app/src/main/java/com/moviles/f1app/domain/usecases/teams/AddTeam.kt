package com.moviles.f1app.domain.usecases.teams

import com.moviles.f1app.data.repository.TeamRepository
import com.moviles.f1app.domain.modelo.Team
import javax.inject.Inject

class AddTeam @Inject constructor(private val teamRepository: TeamRepository) {

    suspend fun invoke(team: Team): Boolean {
        return try {
            teamRepository.addTeam(team)
            true
        } catch (e: Exception) {
            false
        }
    }
}