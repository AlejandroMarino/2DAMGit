package com.example.examenmoviles.data.repository

import com.example.examenmoviles.data.local.dao.PacienteDao
import com.example.examenmoviles.data.modelo.toPacienteConEnfermedades
import com.example.examenmoviles.data.modelo.toPacienteEntity
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.network.services.PacienteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PacienteRepository @Inject constructor(
    private val pacienteDao: PacienteDao,
    private val pacienteService: PacienteService
) {
    fun fetchPacientes(): Flow<List<Paciente>> {
        return flow {
            val result = pacienteService.getPacientes()
            emit(result)
            pacienteDao.deleteAll(
                pacienteService.getPacientes().map { it.toPacienteEntity() })
            pacienteDao.insertAll(
                pacienteService.getPacientes().map { it.toPacienteEntity() })
        }.flowOn(Dispatchers.IO)
    }
}