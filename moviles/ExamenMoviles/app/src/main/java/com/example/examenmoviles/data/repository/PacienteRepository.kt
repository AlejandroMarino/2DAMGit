package com.example.examenmoviles.data.repository

import com.example.examenmoviles.data.local.dao.PacienteDao
import com.example.examenmoviles.data.modelo.toPacienteEntity
import com.example.examenmoviles.data.remote.PacienteRemoteDataSource
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.network.services.PacienteService
import com.example.examenmoviles.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PacienteRepository @Inject constructor(
    private val pacienteDao: PacienteDao,
    private val pacienteRemoteDataSource: PacienteRemoteDataSource
) {
    fun fetchPacientes(): Flow<NetworkResult<List<Paciente>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = pacienteRemoteDataSource.fetchPacientes()
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let {it ->
                    pacienteDao.deleteAll(it.map { it.toPacienteEntity() })
                    pacienteDao.insertAll(it.map { it.toPacienteEntity() })
                }
            }
        }.flowOn(Dispatchers.IO)

    }
}