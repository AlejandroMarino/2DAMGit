package com.moviles.f1app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverRaceCrossRef

@Dao
interface PerformanceDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addPerformance(performance: DriverRaceCrossRef)

    @Query(Constantes.querieGetPerformance)
    suspend fun getPerformance(idDriver: Int, idRace: Int): DriverRaceCrossRef
}