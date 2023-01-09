package com.moviles.f1app.ui.pantalla.admin.list.races

import com.moviles.f1app.domain.modelo.Race

data class EditRacesState(
    val races: List<Race> = emptyList(),
    val error: String? = null
)