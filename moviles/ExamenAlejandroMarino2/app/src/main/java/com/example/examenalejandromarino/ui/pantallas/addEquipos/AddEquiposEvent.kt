package com.example.examenalejandromarino.ui.pantallas.addEquipos

import com.example.examenalejandromarino.domain.modelo.Equipo

sealed class AddEquiposEvent {
    class AddEquipo(val equipo: Equipo): AddEquiposEvent()
}