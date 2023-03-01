package com.example.examenmoviles.data.remote

import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.network.services.PacienteService
import com.example.examenmoviles.utils.NetworkResult
import javax.inject.Inject

class PacienteRemoteDataSource @Inject constructor(private val pacienteService: PacienteService) :
    BaseApiResponse() {

    suspend fun fetchPacientes(): NetworkResult<List<Paciente>> {
        return safeApiCall(apiCall = { pacienteService.getPacientes() })
    }

    suspend fun updatePaciente(paciente: Paciente): NetworkResult<Void> {
        return safeApiCall(apiCall = { pacienteService.updatePaciente(paciente.id, paciente) })
    }

    suspend fun addEnfermedad(paciente: Paciente, enfermedad: Enfermedad): NetworkResult<Void> {
        return safeApiCall(apiCall = { pacienteService.addEnfermedad(paciente.id, enfermedad) })
    }
}
