package com.moviles.appf1teams.ui.pantalla.recycleView

import com.moviles.appf1teams.domain.modelo.Team

data class RecycleViewState (
    val teams: List<Team>,
    val error: String? = null
)
