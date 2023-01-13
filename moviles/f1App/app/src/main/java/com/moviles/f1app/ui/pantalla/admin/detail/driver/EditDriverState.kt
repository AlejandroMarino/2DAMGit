package com.moviles.f1app.ui.pantalla.admin.detail.driver

import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.modelo.Team

data class EditDriverState(
    val driver: Driver = Driver(),
    val teams: List<Team> = emptyList(),
    val message: String? = null,
    val teamName: String = "",
    val performances: List<PerformanceWithObjects> = emptyList(),
)