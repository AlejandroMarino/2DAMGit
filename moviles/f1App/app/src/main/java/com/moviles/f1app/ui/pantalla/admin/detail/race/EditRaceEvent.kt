package com.moviles.f1app.ui.pantalla.admin.detail.race

import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.modelo.Race

sealed class EditRaceEvent {
    class AddRace(val race: Race) : EditRaceEvent()
    class UpdateRace(val race: Race) : EditRaceEvent()
    class GetData(val id: Int) : EditRaceEvent()
    class DeletePerformance(val performance: PerformanceWithObjects) : EditRaceEvent()
    class AddPerformance(val performance: PerformanceWithObjects) : EditRaceEvent()
}