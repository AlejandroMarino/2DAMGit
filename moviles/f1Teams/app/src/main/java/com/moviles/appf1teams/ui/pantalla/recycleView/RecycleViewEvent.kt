package com.moviles.appf1teams.ui.pantalla.recycleView

import com.moviles.appf1teams.domain.modelo.Team

sealed class RecycleViewEvent {

    object LoadTeams : RecycleViewEvent()
    class DeleteTeam(val team: Team) : RecycleViewEvent()
    class AddTeam(val team: Team) : RecycleViewEvent()
}