package com.moviles.appf1teams.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.moviles.appf1teams.data.common.Constantes
import com.moviles.appf1teams.data.modelo.DriverEntity
import com.moviles.appf1teams.data.modelo.TeamEntity
import com.moviles.appf1teams.data.modelo.TeamWithDriver

@Dao
interface TeamDao {
    @Query(Constantes.querieGetAllTeams)
    suspend fun getTeams(): List<TeamWithDriver>

    @Query(Constantes.querieDeleteTeam)
    suspend fun deleteTeam(id: Int)

    @Query(Constantes.querieGetTeam)
    suspend fun getTeam(id: Int): TeamWithDriver

    @Query(Constantes.querieUpdateTeam)
    suspend fun updateTeam(id: Int, name: String, performance: Float, tyre: Int, winner: Boolean)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun insertTeam(team: TeamEntity)

    @Query(Constantes.querieGetDrivers)
    suspend fun getDrivers(id: Int): List<DriverEntity>

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun insertDriver(driver: DriverEntity)

    @Query(Constantes.querieDeleteDriver)
    suspend fun deleteDriver(idDriver: Int)

    @Transaction()
    suspend fun createTransaction(team: TeamWithDriver) {
        team.drivers?.onEach { it.id?.let { it1 -> deleteDriver(it1) } }
        deleteTeam(team.team.id)
    }

}
