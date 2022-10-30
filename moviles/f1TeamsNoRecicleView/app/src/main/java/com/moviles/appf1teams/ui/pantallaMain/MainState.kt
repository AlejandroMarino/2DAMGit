package com.moviles.appf1teams.ui.pantallaMain

import com.moviles.appf1teams.domain.modelo.Team

data class MainState(
    val team: Team = Team(),
    val error: String? = null
)

