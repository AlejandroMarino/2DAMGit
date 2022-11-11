package com.moviles.appf1teams.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moviles.appf1teams.data.modelo.TeamEntity

@Dao
interface TeamDao {
    @Query("SELECT * from teams")
    suspend fun getTeams(): List<TeamEntity>

    @Query("DELETE FROM teams WHERE id = :id")
    suspend fun deleteTeam(id: Int)

    @Query("SELECT * from teams where id = :id")
    suspend fun getTeam(id: Int): TeamEntity

    @Query("UPDATE teams SET name = :name, performance = :performance, tyre = :tyre, winner = :winner WHERE id = :id")
    suspend fun updateTeam(id: Int, name: String, performance: Float, tyre: Int, winner: Boolean)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun insertTeam(team: TeamEntity)
}
