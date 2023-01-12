package com.moviles.f1app.domain.usecases.performances

import com.moviles.f1app.data.repository.PerformanceRepository
import javax.inject.Inject

class GetDriverPerformances @Inject constructor(private val performanceRepository: PerformanceRepository) {

    suspend fun invoke(idDriver: Int) = performanceRepository.getDriverPerformances(idDriver)
}
