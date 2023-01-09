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

@Dao
interface RaceDao {
    @Query(Constantes.querieGetRaces)
    suspend fun getRaces(): List<RaceWithDrivers>

    @Query(Constantes.querieGetRace)
    suspend fun getRace(id: Int): RaceWithDrivers

    @Insert
    suspend fun addRace(race: RaceEntity)

    @Delete
    @Transaction
    suspend fun deleteRace(race: RaceEntity, performance: List<DriverRaceCrossRef>)
}