package com.moviles.f1app.ui.pantalla.user.detail.team

sealed class WatchTeamEvent {
    class GetTeam(val idTeam: Int) : WatchTeamEvent()
}