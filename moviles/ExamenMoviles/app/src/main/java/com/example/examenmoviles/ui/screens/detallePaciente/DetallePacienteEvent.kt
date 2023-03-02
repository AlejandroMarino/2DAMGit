package com.example.examenmoviles.ui.screens.detallePaciente

import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente

sealed class DetallePacienteEvent {
    class GetPaciente(val id: String) : DetallePacienteEvent()
    class UpdatePaciente(val paciente: Paciente) : DetallePacienteEvent()
    class AddEnfermedad(val enfermedad: Enfermedad) : DetallePacienteEvent()
    class OnValuePacienteChange(val paciente: Paciente) : DetallePacienteEvent()
    class OnValueEnfermedadChange(val enfermedad: Enfermedad) : DetallePacienteEvent()
    object ErrorCatch : DetallePacienteEvent()
}