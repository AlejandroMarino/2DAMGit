package com.moviles.appf1teams.ui.pantalla.drivers

import com.moviles.appf1teams.domain.modelo.Driver

data class DriversState (
    val drivers: List<Driver> = emptyList(),
    val message: String? = null,
)