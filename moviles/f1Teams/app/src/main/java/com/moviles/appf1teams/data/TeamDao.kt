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

    @Query("DELETE FROM teams WHERE name = :name")
    suspend fun deleteTeam(name: String)

    @Query("SELECT * from teams where name = :name")
    suspend fun getTeam(name: String): TeamEntity

    @Query("UPDATE teams SET name = :name, performance = :performance, tyre = :tyre, winner = :winner WHERE name = :newName")
    suspend fun updateTeam(name : String, newName: String, performance: Float, tyre: Int, winner: Boolean)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun insertTeam(team: TeamEntity)
}
