package com.moviles.f1app.data.modelo

import com.moviles.f1app.domain.modelo.Driver

data class DriverRaceWithNames(
    val driver: DriverEntity,
    val race: RaceEntity,
    val position: Int,
    val fastestLap: String,
)