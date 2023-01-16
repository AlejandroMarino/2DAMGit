package com.moviles.f1app.ui.pantalla.user.detail.team

import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.Team

data class WatchTeamState(
    val team: Team = Team(),
    val drivers: List<Driver> = emptyList()
)