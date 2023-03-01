package com.example.examenmoviles.domain.usecases.pacientes

import com.example.examenmoviles.data.repository.PacienteRepository
import com.example.examenmoviles.domain.modelo.Paciente
import java.util.UUID
import javax.inject.Inject

class GetPaciente @Inject constructor(private val pacienteRepository: PacienteRepository) {

    suspend fun invoke(id: UUID): Paciente{
        var paciente = Paciente(UUID.randomUUID(), "", "")
        pacienteRepository.fetchPacientes().collect{
            paciente =  it.data?.stream()?.filter { paciente -> paciente.id == id }?.findFirst()?.get() ?: paciente
        }
        return paciente
    }
}