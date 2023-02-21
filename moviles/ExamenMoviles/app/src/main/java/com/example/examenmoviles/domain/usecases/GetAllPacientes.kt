package com.example.examenmoviles.domain.usecases

import com.example.examenmoviles.data.repository.PacienteRepository
import javax.inject.Inject

class GetAllPacientes @Inject constructor(private val pacienteRepository: PacienteRepository) {
    suspend operator fun invoke() = pacienteRepository.fetchPacientes()
}