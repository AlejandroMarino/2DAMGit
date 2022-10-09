package com.moviles.appf1teams.ui.pantallaMain

import com.moviles.appf1teams.domain.modelo.Team

class MainState(
    val team: Team = Team("null",50F,2,false),
    val error: String? = null
)

