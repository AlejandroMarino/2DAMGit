package com.example.examenmoviles.network.services

import com.example.examenmoviles.common.Constantes
import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.*

interface PacienteService {

    @GET(Constantes.getPacientes)
    suspend fun getPacientes(): Response<List<Paciente>>

    @PUT("/pacientes/{id}")
        suspend fun updatePaciente(@Path("id") id: UUID, @Body paciente: Paciente): Response<Void>

    @POST("/pacientes/{id}")
    suspend fun addEnfermedad(@Path("id") id: UUID, @Body enfermedad: Enfermedad): Response<Void>
}