package com.example.examenmoviles.domain.usecases.pacientes

import com.example.examenmoviles.data.repository.PacienteRepository
import javax.inject.Inject

class GetAllPacientes @Inject constructor(private val pacienteRepository: PacienteRepository) {
    operator fun invoke() = pacienteRepository.fetchPacientes()
}