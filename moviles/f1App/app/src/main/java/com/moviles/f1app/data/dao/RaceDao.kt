package com.moviles.f1app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.RaceEntity
import com.moviles.f1app.data.modelo.relacciones.RaceWithDrivers
import java.time.LocalDate

@Dao
interface RaceDao {
    @Query(Constantes.queryGetRaces)
    suspend fun getRaces(): List<RaceWithDrivers>

    @Query(Constantes.queryGetRace)
    suspend fun getRace(id: Int): RaceWithDrivers

    @Query(Constantes.queryGetRaceByTrack)
    suspend fun getRaceByTrack(track: String): RaceWithDrivers

    @Insert
    suspend fun addRace(race: RaceEntity)

    @Query(Constantes.queryUpdateRace)
    suspend fun updateRace(id: Int, track: String, date: LocalDate)

    @Delete
    @Transaction
    suspend fun deleteRace(race: RaceEntity, performance: List<DriverRaceCrossRef>)
}