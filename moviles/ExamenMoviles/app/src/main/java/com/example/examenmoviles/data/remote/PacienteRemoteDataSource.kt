package com.example.examenmoviles.data.remote

import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.network.services.PacienteService
import com.example.examenmoviles.utils.NetworkResult
import javax.inject.Inject

class PacienteRemoteDataSource @Inject constructor(private val pacienteService: PacienteService) :
    BaseApiResponse() {

    suspend fun fetchPacientes(): NetworkResult<List<Paciente>> {
        return safeApiCall(apiCall = { pacienteService.getPacientes() })
    }
}
