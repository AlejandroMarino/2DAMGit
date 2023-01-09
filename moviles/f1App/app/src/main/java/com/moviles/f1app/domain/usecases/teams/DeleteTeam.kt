package com.moviles.f1app.domain.usecases.teams

import com.moviles.f1app.data.repository.TeamRepository
import javax.inject.Inject

class DeleteTeam @Inject constructor(private val teamRepository: TeamRepository) {

    suspend fun invoke(id: Int): Boolean {
        val team = teamRepository.getTeam(id)
        return try {
            teamRepository.deleteTeam(team)
            true
        } catch (e: Exception) {
            false
        }
    }
}