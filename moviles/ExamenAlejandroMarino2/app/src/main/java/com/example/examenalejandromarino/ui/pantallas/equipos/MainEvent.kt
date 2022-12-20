package com.example.examenalejandromarino.ui.pantallas.equipos

sealed class MainEvent {
    object LoadEquipos : MainEvent()
    class DeleteEquipo(val id: Int): MainEvent()
}