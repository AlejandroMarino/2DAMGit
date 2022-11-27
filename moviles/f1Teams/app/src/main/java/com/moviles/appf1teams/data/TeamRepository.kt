package com.moviles.appf1teams.data

import com.moviles.appf1teams.data.modelo.*
import com.moviles.appf1teams.domain.modelo.Driver
import com.moviles.appf1teams.domain.modelo.Team
import javax.inject.Inject

class TeamRepository @Inject constructor(private val teamDao: TeamDao) {

    suspend fun getTeams() = teamDao.getTeams().map { it.toTeam() }

    suspend fun getTeam(id: Int) = teamDao.getTeam(id).toTeam()

    suspend fun deleteTeam(team: Team) = teamDao.createTransaction(team.toTeamWithDriver())

    suspend fun updateTeam(
        id: Int,
        name: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ) = teamDao.updateTeam(id, name, performance, tyre, winner)

    suspend fun addTeam(team: Team) = teamDao.insertTeam(team.toTeamEntity())

    suspend fun getDrivers(id: Int) = teamDao.getDrivers(id).map { it.toDriver() }

    suspend fun addDriver(id: Int, driver: Driver) = teamDao.insertDriver(driver.toDriverEntity(id))

    suspend fun deleteDriver(idDriver: Int) = teamDao.deleteDriver(idDriver)

}