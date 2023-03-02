package com.example.examenmoviles.domain.usecases.pacientes

import com.example.examenmoviles.data.repository.PacienteRepository
import com.example.examenmoviles.domain.modelo.Paciente
import javax.inject.Inject

class UpdatePaciente @Inject constructor(private val pacienteRepository: PacienteRepository) {

    operator fun invoke(paciente: Paciente) = pacienteRepository.updatePaciente(paciente)
}