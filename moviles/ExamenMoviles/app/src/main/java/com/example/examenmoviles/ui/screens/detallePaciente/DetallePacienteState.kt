package com.example.examenmoviles.ui.screens.detallePaciente

import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente
import java.util.*

data class DetallePacienteState(
    val paciente: Paciente = Paciente(UUID.randomUUID(), "", ""),
    val enfermedad: Enfermedad = Enfermedad(""),
    val isLoading: Boolean = false,
    val error: String = ""
)