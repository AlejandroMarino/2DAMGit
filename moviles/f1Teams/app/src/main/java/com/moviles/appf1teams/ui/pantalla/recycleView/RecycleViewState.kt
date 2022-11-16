package com.moviles.appf1teams.ui.pantalla.recycleView

import com.moviles.appf1teams.domain.modelo.Team

data class RecycleViewState (
    var teams: List<Team> = emptyList(),
    val error: String? = null
)
