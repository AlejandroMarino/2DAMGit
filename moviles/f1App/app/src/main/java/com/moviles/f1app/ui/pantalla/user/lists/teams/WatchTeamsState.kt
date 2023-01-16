package com.moviles.f1app.ui.pantalla.user.lists.teams

import com.moviles.f1app.domain.modelo.Team

data class WatchTeamsState(
    var teams: List<Team> = emptyList(),
)