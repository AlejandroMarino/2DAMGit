package com.moviles.f1app.ui.pantalla.admin.detail.race

import com.moviles.f1app.domain.modelo.Race

data class EditRaceState(
    val race: Race = Race(),
    val message: String? = null,
)