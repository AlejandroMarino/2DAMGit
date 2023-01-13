package com.moviles.f1app.domain.modelo

data class Performance(
    var idDriver: Int = 0,
    var idRace: Int = 0,
    val position: Int = 0,
    val fastestLap: String = "",
)