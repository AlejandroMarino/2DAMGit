package com.moviles.f1app.ui.pantalla.admin.list.drivers

import com.moviles.f1app.domain.modelo.Driver

sealed class EditDriversEvent {

    object LoadDrivers : EditDriversEvent()
    class DeleteDriver(val driver: Driver) : EditDriversEvent()
    class AddDriver(val driver: Driver) : EditDriversEvent()
}