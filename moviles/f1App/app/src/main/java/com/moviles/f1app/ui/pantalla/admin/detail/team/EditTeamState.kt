package com.moviles.f1app.ui.pantalla.admin.detail.team

import com.moviles.f1app.domain.modelo.Team

data class EditTeamState (
    val team: Team = Team(),
    val message: String? = null
)
