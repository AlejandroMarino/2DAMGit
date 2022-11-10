package com.moviles.appf1teams.data

import com.moviles.appf1teams.data.modelo.TeamEntity
import com.moviles.appf1teams.data.modelo.toTeam
import com.moviles.appf1teams.data.modelo.toTeamEntity
import com.moviles.appf1teams.domain.modelo.Team

class TeamRepository(private val TeamDao: TeamDao) {

    suspend fun getTeams() = TeamDao.getTeams().map { it.toTeam() }

    suspend fun getTeam(name: String) = TeamDao.getTeam(name).toTeam()

    suspend fun deleteTeam(name: String) = TeamDao.deleteTeam(name)

    suspend fun updateTeam(
        name: String,
        newName: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ) = TeamDao.updateTeam(name, newName, performance, tyre, winner)

    suspend fun addTeam(team: Team) = TeamDao.insertTeam(team.toTeamEntity())

}