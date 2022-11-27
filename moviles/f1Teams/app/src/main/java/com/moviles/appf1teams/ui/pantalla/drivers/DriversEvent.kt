package com.moviles.appf1teams.ui.pantalla.drivers

import com.moviles.appf1teams.domain.modelo.Driver


sealed class DriversEvent {

    class LoadDrivers(val idTeam: Int) : DriversEvent()
    class DeleteDriver(val id: Int, val driver: Driver) : DriversEvent()
    class AddDriver(val id: Int, val driver: Driver) : DriversEvent()
}