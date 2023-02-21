package com.example.examenmoviles.network.services

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.example.examenmoviles.common.Constantes
import com.example.examenmoviles.data.modelo.relaciones.PacienteConEnfermedades
import com.example.examenmoviles.domain.modelo.Paciente
import retrofit2.http.GET

interface PacienteService {

    @GET(Constantes.getPacientes)
    suspend fun getPacientes(): List<Paciente>

}