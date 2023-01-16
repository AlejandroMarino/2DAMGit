package com.moviles.f1app.ui.pantalla.admin.detail.driver

import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.PerformanceWithObjects

sealed class EditDriverEvent {
    class AddDriver(val driver: Driver, val teamName: String) : EditDriverEvent()
    class UpdateDriver(val driver: Driver, val teamName: String) : EditDriverEvent()
    class GetDriver(val id: Int) : EditDriverEvent()
    class DeletePerformance(val performance: PerformanceWithObjects) : EditDriverEvent()
    class AddPerformance(val performance: PerformanceWithObjects) : EditDriverEvent()
    class SetPhoto(val photo: String) : EditDriverEvent()
    object GetTeams : EditDriverEvent()
}