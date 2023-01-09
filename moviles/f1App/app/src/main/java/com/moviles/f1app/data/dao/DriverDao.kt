package com.moviles.f1app.data.dao

import androidx.room.*
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.relacciones.DriverWithRaces

@Dao
interface DriverDao {

    @Query(Constantes.querieGetDrivers)
    suspend fun getDrivers(): List<DriverWithRaces>

    @Query(Constantes.querieGetDriversOfTeam)
    suspend fun getDriversOfTeam(idTeam: Int): List<DriverWithRaces>

    @Query(Constantes.querieGetDriver)
    suspend fun getDriver(id: Int): DriverWithRaces

    @Delete
    @Transaction
    suspend fun deleteDriver(driver: DriverEntity, performance: List<DriverRaceCrossRef>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addDriver(driver: DriverEntity)

}