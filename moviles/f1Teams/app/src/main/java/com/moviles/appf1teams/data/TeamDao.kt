package com.moviles.appf1teams.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.moviles.appf1teams.data.common.Constantes
import com.moviles.appf1teams.data.modelo.TeamEntity

@Dao
interface TeamDao {
    @Query(Constantes.querieGetAllTeams)
    suspend fun getTeams(): List<TeamEntity>

    @Query(Constantes.querieDeleteTeam)
    suspend fun deleteTeam(id: Int)

    @Query(Constantes.querieGetTeam)
    suspend fun getTeam(id: Int): TeamEntity

    @Query(Constantes.querieUpdateTeam)
    suspend fun updateTeam(id: Int, name: String, performance: Float, tyre: Int, winner: Boolean)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun insertTeam(team: TeamEntity)
}
