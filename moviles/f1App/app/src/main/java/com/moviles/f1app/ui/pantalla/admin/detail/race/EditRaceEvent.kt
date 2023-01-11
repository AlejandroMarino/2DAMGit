package com.moviles.f1app.ui.pantalla.admin.detail.race

import com.moviles.f1app.domain.modelo.Race

sealed class EditRaceEvent {
    class AddRace(val race: Race) : EditRaceEvent()
    class UpdateRace(val race: Race) : EditRaceEvent()
    class GetRace(val id: Int) : EditRaceEvent()
}