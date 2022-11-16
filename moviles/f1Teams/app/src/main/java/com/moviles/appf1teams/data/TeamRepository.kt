package com.moviles.appf1teams.data

import com.moviles.appf1teams.data.modelo.toTeam
import com.moviles.appf1teams.data.modelo.toTeamEntity
import com.moviles.appf1teams.domain.modelo.Team

class TeamRepository(private val teamDao: TeamDao) {

    suspend fun getTeams() = teamDao.getTeams().map { it.toTeam() }

    suspend fun getTeam(id: Int) = teamDao.getTeam(id).toTeam()

    suspend fun deleteTeam(id: Int) = teamDao.deleteTeam(id)

    suspend fun updateTeam(
        id: Int,
        name: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ) = teamDao.updateTeam(id, name, performance, tyre, winner)

    suspend fun addTeam(team: Team) = teamDao.insertTeam(team.toTeamEntity())

}