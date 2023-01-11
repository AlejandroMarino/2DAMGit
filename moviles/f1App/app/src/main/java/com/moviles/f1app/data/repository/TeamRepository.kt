package com.moviles.f1app.data.repository

import com.moviles.f1app.data.dao.TeamDao
import com.moviles.f1app.data.modelo.toDriverEntity
import com.moviles.f1app.data.modelo.toTeam
import com.moviles.f1app.data.modelo.toTeamEntity
import com.moviles.f1app.data.modelo.toTeamWithDriver
import com.moviles.f1app.domain.modelo.Team
import javax.inject.Inject

class TeamRepository @Inject constructor(private val teamDao: TeamDao) {

    suspend fun getTeams() = teamDao.getTeams().map { it.toTeam() }

    suspend fun getTeam(id: Int) = teamDao.getTeam(id).toTeam()

    suspend fun getTeamByName(name: String) = teamDao.getTeamByName(name).toTeam()

    suspend fun deleteTeam(team: Team) =
        teamDao.deleteTeam(team.toTeamEntity(), team.drivers.map { it.toDriverEntity() })

    suspend fun addTeam(team: Team) = teamDao.addTeam(team.toTeamEntity())

    suspend fun updateTeam(id: Int, name: String, car: String) = teamDao.updateTeam(id, name, car)
}