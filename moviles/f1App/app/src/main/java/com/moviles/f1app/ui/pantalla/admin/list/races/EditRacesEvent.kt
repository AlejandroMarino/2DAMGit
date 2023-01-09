package com.moviles.f1app.ui.pantalla.admin.list.races

import com.moviles.f1app.domain.modelo.Race

sealed class EditRacesEvent {

    object LoadRaces : EditRacesEvent()
    class DeleteRace(val race: Race) : EditRacesEvent()
    class AddRace(val race: Race): EditRacesEvent()
}