package com.moviles.f1app.domain.usecases.performances

import com.moviles.f1app.data.repository.PerformanceRepository
import com.moviles.f1app.domain.modelo.Performance
import javax.inject.Inject

class AddPerformance @Inject constructor(private val performanceRepository: PerformanceRepository) {
    suspend fun invoke(performance: Performance): Boolean {
        return try {
            performanceRepository.addPerformance(performance)
            true
        } catch (e: Exception) {
            false
        }
    }
}
