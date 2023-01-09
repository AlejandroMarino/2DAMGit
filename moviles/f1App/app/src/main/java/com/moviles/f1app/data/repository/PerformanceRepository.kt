package com.moviles.f1app.data.repository

import com.moviles.f1app.data.dao.PerformanceDao
import com.moviles.f1app.data.modelo.toDriverRaceCrossRef
import com.moviles.f1app.domain.modelo.Performance
import javax.inject.Inject

class PerformanceRepository @Inject constructor(private val performanceDao: PerformanceDao) {

    suspend fun getPerformance(idDriver: Int, idRace: Int) = performanceDao.getPerformance(idDriver, idRace)

    suspend fun addPerformance(performance: Performance) = performanceDao.addPerformance(performance.toDriverRaceCrossRef())

}