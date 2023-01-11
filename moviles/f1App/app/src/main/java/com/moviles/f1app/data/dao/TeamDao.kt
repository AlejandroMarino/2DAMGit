package com.moviles.f1app.data.dao

import androidx.room.*
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.TeamEntity
import com.moviles.f1app.data.modelo.relacciones.TeamWithDrivers

@Dao
interface TeamDao {

    @Query(Constantes.querieGetAllTeams)
    suspend fun getTeams(): List<TeamWithDrivers>

    @Query(Constantes.querieGetTeam)
    suspend fun getTeam(id: Int): TeamWithDrivers

    @Query(Constantes.querieGetTeamByName)
    suspend fun getTeamByName(name: String) : TeamWithDrivers

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTeam(team: TeamEntity)

    @Delete
    @Transaction
    suspend fun deleteTeam(team: TeamEntity, drivers: List<DriverEntity>)

    @Query(Constantes.querieUpdateTeam)
    suspend fun updateTeam(id: Int, name: String, car: String)
}
