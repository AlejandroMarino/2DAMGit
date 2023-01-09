package com.moviles.f1app.ui.pantalla.admin.detail.team

import com.moviles.f1app.domain.modelo.Team

sealed class EditTeamEvent {

    class AddTeam(val team: Team) : EditTeamEvent()
}