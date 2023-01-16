package com.moviles.f1app.ui.pantalla.user.detail.race

sealed class WatchRaceEvent {
    class GetRace(val id: Int) : WatchRaceEvent()
}