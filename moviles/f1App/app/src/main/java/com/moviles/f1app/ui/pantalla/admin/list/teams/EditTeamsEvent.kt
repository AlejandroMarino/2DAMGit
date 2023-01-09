package com.moviles.f1app.ui.pantalla.admin.list.teams

import com.moviles.f1app.domain.modelo.Team

sealed class EditTeamsEvent {

    object LoadTeams : EditTeamsEvent()
    class DeleteTeam(val team: Team) : EditTeamsEvent()
    class AddTeam(val team: Team) : EditTeamsEvent()
}