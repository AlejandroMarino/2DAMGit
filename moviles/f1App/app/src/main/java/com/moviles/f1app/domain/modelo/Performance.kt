package com.moviles.f1app.domain.modelo

data class Performance(
    val idDriver: Int = 0,
    val idRace: Int = 0,
    val position: Int,
    val fastestLap: String,
)