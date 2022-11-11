package com.moviles.appf1teams.data

import com.moviles.appf1teams.data.modelo.TeamEntity
import com.moviles.appf1teams.data.modelo.toTeam
import com.moviles.appf1teams.data.modelo.toTeamEntity
import com.moviles.appf1teams.domain.modelo.Team

class TeamRepository(private val TeamDao: TeamDao) {

    suspend fun getTeams() = TeamDao.getTeams().map { it.toTeam() }

    suspend fun getTeam(id: Int) = TeamDao.getTeam(id).toTeam()

    suspend fun deleteTeam(id: Int) = TeamDao.deleteTeam(id)

    suspend fun updateTeam(
        id: Int,
        name: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ) = TeamDao.updateTeam(id,name, performance, tyre, winner)

    suspend fun addTeam(team: Team) = TeamDao.insertTeam(team.toTeamEntity())

}