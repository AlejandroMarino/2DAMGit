package com.moviles.f1app.data.modelo

import com.moviles.f1app.domain.modelo.Race

data class DriverRaceConNombres(
    val race: RaceEntity,
    val driver: DriverEntity,
    val position: Int,
    val fastestLap: String,
)