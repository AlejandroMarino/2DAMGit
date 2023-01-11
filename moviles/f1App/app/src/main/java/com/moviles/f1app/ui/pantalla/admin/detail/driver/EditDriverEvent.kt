package com.moviles.f1app.ui.pantalla.admin.detail.driver

import com.moviles.f1app.domain.modelo.Driver

sealed class EditDriverEvent {
    class AddDriver(val driver: Driver, val teamName: String) : EditDriverEvent()
    class UpdateDriver(val driver: Driver, val teamName: String) : EditDriverEvent()
    class GetDriver(val id: Int) : EditDriverEvent()
    object GetTeams : EditDriverEvent()
}