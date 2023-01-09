package com.moviles.f1app.ui.pantalla.admin.list.drivers

import com.moviles.f1app.domain.modelo.Driver

data class EditDriversState(
    val drivers: List<Driver> = emptyList(),
    val error: String? = null
)
