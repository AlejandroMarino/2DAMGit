package com.moviles.appf1teams.ui.pantallaMain

import com.moviles.appf1teams.domain.modelo.Team

class MainState(
    val persona: Team = Team("null", "null", "null", 0),
    val error: String? = null
)