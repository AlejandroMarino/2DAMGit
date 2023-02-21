package com.example.examenmoviles.ui.screens.pacientes

import com.example.examenmoviles.domain.modelo.Paciente

data class PacientesState(
    val pacientes: List<Paciente> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)