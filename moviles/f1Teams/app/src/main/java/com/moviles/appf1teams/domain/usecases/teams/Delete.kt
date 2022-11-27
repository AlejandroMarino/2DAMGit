package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository
import javax.inject.Inject

class Delete @Inject constructor(private val teamRepository: TeamRepository) {

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