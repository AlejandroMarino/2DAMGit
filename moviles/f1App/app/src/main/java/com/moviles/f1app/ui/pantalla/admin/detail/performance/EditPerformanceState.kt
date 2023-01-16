package com.moviles.f1app.ui.pantalla.admin.detail.performance

import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.modelo.Race

data class EditPerformanceState (
    val performance: PerformanceWithObjects = PerformanceWithObjects(),
    val message: String? = null,
    val races: List<Race> = emptyList(),
    val drivers: List<Driver> = emptyList(),
    val driverName: String = "",
    val trackName: String = "",
)