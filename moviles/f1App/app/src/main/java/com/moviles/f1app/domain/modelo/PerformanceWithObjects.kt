package com.moviles.f1app.domain.modelo

data class PerformanceWithObjects(
    val driver: Driver = Driver(),
    val race: Race = Race(),
    val position: Int = 0,
    val fastestLap: String = "",
)