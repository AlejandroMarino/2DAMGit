package com.moviles.appf1teams.ui.pantalla.main

import com.moviles.appf1teams.domain.modelo.Team

data class MainState(
    val team: Team = Team(),
    val message: String? = null
)

