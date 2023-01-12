package com.moviles.f1app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.domain.modelo.Performance

@Dao
interface PerformanceDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addPerformance(performance: DriverRaceCrossRef)

    @Query(Constantes.querieUpdatePerformance)
    suspend fun updatePerformance(idDriver: Int, idRace: Int, position: Int, fastestLap: String)

    @Delete
    suspend fun deletePerformance(performance: DriverRaceCrossRef)

    @Query(Constantes.querieGetPerformance)
    suspend fun getPerformance(idDriver: Int, idRace: Int): DriverRaceCrossRef

    @Query(Constantes.querieGerDriverPerformances)
    suspend fun getDriverPerformances(idDriver: Int): List<DriverRaceCrossRef>

    @Query(Constantes.querieGerRacePerformances)
    suspend fun getRacePerformances(idRace: Int): List<DriverRaceCrossRef>
}