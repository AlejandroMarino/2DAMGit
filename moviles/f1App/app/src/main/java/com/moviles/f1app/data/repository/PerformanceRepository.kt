package com.moviles.f1app.data.repository

import com.moviles.f1app.data.dao.PerformanceDao
import com.moviles.f1app.data.modelo.toDriverRaceCrossRef
import com.moviles.f1app.data.modelo.toPerformance
import com.moviles.f1app.data.modelo.toPerformanceWithObjects
import com.moviles.f1app.domain.modelo.Performance
import javax.inject.Inject

class PerformanceRepository @Inject constructor(private val performanceDao: PerformanceDao) {

    suspend fun getPerformance(idDriver: Int, idRace: Int) =
        performanceDao.getPerformance(idDriver, idRace).toPerformance()

    suspend fun getPerformanceWithObjects(idDriver: Int, idRace: Int) =
        performanceDao.getPerformanceWithObjects(idDriver, idRace).toPerformanceWithObjects()

    suspend fun getDriverPerformances(idDriver: Int) =
        performanceDao.getDriverPerformances(idDriver).map { it.toPerformanceWithObjects() }

    suspend fun getRacePerformances(idRace: Int) =
        performanceDao.getRacePerformances(idRace).map { it.toPerformanceWithObjects() }

    suspend fun addPerformance(performance: Performance) =
        performanceDao.addPerformance(performance.toDriverRaceCrossRef())

    suspend fun updatePerformance(performance: Performance) = performanceDao.updatePerformance(
        performance.idDriver,
        performance.idRace,
        performance.position,
        performance.fastestLap
    )

    suspend fun deletePerformance(performance: Performance) =
        performanceDao.deletePerformance(performance.toDriverRaceCrossRef())

}