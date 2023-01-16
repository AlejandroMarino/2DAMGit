package com.moviles.f1app.ui.pantalla.user.detail.driver

import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.PerformanceWithObjects

data class WatchDriverState(
    val driver: Driver = Driver(),
    val teamName : String = "",
    val performances: List<PerformanceWithObjects> = emptyList(),
)