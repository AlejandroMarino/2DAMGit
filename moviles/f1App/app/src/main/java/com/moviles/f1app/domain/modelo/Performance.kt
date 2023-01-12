package com.moviles.f1app.domain.modelo

data class Performance(
    var driver: Driver = Driver(),
    var race: Race = Race(),
    val position: Int = 0,
    val fastestLap: String = "",
)