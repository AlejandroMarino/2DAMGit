package com.moviles.f1app.ui.pantalla.admin.detail.performance

import com.moviles.f1app.domain.modelo.Performance

sealed class EditPerformanceEvent {
    object GetData : EditPerformanceEvent()
    data class GetPerformance(val idDriver: Int, val idRace: Int) : EditPerformanceEvent()
    data class AddPerformance(
        val performance: Performance,
        val driverName: String,
        val trackName: String
    ) : EditPerformanceEvent()

    data class UpdatePerformance(
        val performance: Performance,
        val driverName: String,
        val trackName: String
    ) : EditPerformanceEvent()
}