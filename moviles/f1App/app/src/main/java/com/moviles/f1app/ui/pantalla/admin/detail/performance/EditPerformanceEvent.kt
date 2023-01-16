package com.moviles.f1app.ui.pantalla.admin.detail.performance

import com.moviles.f1app.domain.modelo.Performance

sealed class EditPerformanceEvent {
    class GetData(val idDriver: Int, val idRace: Int) : EditPerformanceEvent()
    class GetPerformance(val driverName: String, val trackName: String) : EditPerformanceEvent()
    class AddPerformance(
        val performance: Performance,
        val driverName: String,
        val trackName: String
    ) : EditPerformanceEvent()

    class UpdatePerformance(
        val performance: Performance,
        val driverName: String,
        val trackName: String
    ) : EditPerformanceEvent()

    class GetDriverName(val idDriver: Int) : EditPerformanceEvent()
    class GetTrackName(val idRace: Int) : EditPerformanceEvent()
    class GetPerformanceByNames(val driverName: String, val trackName: String) : EditPerformanceEvent()
}