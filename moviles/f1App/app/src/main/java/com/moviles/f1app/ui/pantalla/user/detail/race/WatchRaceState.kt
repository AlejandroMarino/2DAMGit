package com.moviles.f1app.ui.pantalla.user.detail.race

import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.modelo.Race

data class WatchRaceState(
    val race: Race = Race(),
    val performances: List<PerformanceWithObjects> = emptyList(),
)