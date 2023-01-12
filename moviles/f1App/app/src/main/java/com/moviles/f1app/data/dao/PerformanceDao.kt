package com.moviles.f1app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.DriverRaceWithNames

@Dao
interface PerformanceDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addPerformance(performance: DriverRaceCrossRef)

    @Query(Constantes.queryUpdatePerformance)
    suspend fun updatePerformance(idDriver: Int, idRace: Int, position: Int, fastestLap: String)

    @Delete
    suspend fun deletePerformance(performance: DriverRaceCrossRef)

    @Query(Constantes.queryGetPerformance)
    suspend fun getPerformance(idDriver: Int, idRace: Int): DriverRaceCrossRef

    @Query("SELECT DriverEntity.idDriver, DriverEntity.name, DriverEntity.number, DriverEntity.id_team, " +
            "RaceEntity.idRace, RaceEntity.track, RaceEntity.date, " +
            "DriverRaceCrossRef.position, DriverRaceCrossRef.fastestLap" +
            "FROM DriverEntity" +
            "INNER JOIN DriverRaceCrossRef ON DriverEntity.idDriver = DriverRaceCrossRef.idDriver" +
            "INNER JOIN RaceEntity ON DriverRaceCrossRef.idRace = RaceEntity.idRace")
    suspend fun getDriverPerformances(idDriver: Int): List<DriverRaceWithNames>p

    @Query(Constantes.queryGerRacePerformances)
    suspend fun getRacePerformances(idRace: Int): List<DriverRaceWithNames>
}