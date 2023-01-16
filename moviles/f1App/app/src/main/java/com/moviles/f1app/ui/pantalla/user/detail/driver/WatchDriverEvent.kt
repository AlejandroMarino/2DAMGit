package com.moviles.f1app.ui.pantalla.user.detail.driver

sealed class WatchDriverEvent {
    class GetDriver(val id: Int) : WatchDriverEvent()
}