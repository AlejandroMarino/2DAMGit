package com.moviles.f1app.data.dao

import androidx.room.*
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.relacciones.DriverWithRaces

@Dao
interface DriverDao {

    @Query(Constantes.queryGetDrivers)
    suspend fun getDrivers(): List<DriverWithRaces>

    @Query(Constantes.queryGetDriversOfTeam)
    suspend fun getDriversOfTeam(idTeam: Int): List<DriverWithRaces>

    @Query(Constantes.queryGetDriver)
    suspend fun getDriver(id: Int): DriverWithRaces

    @Query(Constantes.queryGetDriverByName)
    suspend fun getDriverByName(name: String): DriverWithRaces

    @Query(Constantes.queryUpdateDriver)
    suspend fun updateDriver(id: Int, name: String, number: Int, idTeam: Int)

    @Delete
    @Transaction
    suspend fun deleteDriver(driver: DriverEntity, performance: List<DriverRaceCrossRef>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addDriver(driver: DriverEntity)

}