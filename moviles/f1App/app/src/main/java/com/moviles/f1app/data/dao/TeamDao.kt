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

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    fun addTeam(team: TeamEntity)

    @Delete
    @Transaction
    suspend fun deleteTeam(team: TeamEntity, drivers: List<DriverEntity>)

//    @Query(Constantes.querieUpdateTeam)
//    suspend fun updateTeam(id: Int, name: String, car: String)
}
