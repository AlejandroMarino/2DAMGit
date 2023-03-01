package com.example.examenmoviles.ui.screens.pacientes

sealed class PacientesEvent {
    object GetPacientes : PacientesEvent()
    class FilterPacientes(val text: String) : PacientesEvent()

}