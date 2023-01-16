package com.moviles.f1app.ui.pantalla.user.lists.drivers

import com.moviles.f1app.domain.modelo.Driver

data class WatchDriversState (
    var drivers: List<Driver> = emptyList(),
)