package com.moviles.appf1teams.ui.pantalla.main

import com.moviles.appf1teams.domain.modelo.Team

sealed class MainEvent {

    class LoadTeam(val team: Team) : MainEvent()
    object PreviousTeam : MainEvent()
    object NextTeam : MainEvent()
    class AddTeam(val team: Team) : MainEvent()
    object DeleteTeam : MainEvent()
    class UpdateTeam(val name: String, val performance: Float, val tyre: Int, val winner: Boolean) :
        MainEvent()
}