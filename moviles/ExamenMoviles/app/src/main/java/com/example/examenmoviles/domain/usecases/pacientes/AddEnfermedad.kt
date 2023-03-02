package com.example.examenmoviles.domain.usecases.pacientes

import com.example.examenmoviles.data.repository.PacienteRepository
import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente
import javax.inject.Inject

class AddEnfermedad @Inject constructor(
    private val pacienteRepository: PacienteRepository
) {
    fun invoke(paciente: Paciente, enfermedad: Enfermedad) =
        pacienteRepository.addEnfermedad(paciente, enfermedad)

}