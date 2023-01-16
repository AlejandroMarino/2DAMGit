package com.moviles.f1app.ui.pantalla.user.lists.races

import com.moviles.f1app.domain.modelo.Race

data class WatchRacesState(
    var races: List<Race> = emptyList(),
)