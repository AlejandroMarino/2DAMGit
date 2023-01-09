package com.moviles.f1app.ui.pantalla.admin.list.teams

import com.moviles.f1app.domain.modelo.Team

data class EditTeamsState(
    var teams: List<Team> = emptyList(),
    val error: String? = null
)