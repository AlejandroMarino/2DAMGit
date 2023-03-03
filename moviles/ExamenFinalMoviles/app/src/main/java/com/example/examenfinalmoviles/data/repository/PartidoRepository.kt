package com.example.examenfinalmoviles.data.repository

import com.example.examenfinalmoviles.data.local.dao.PartidoDao
import com.example.examenfinalmoviles.data.modelo.toPartidoEntity
import com.example.examenfinalmoviles.data.remote.PartidoRemoteDataSource
import com.example.examenfinalmoviles.domain.modelo.Partido
import com.example.examenfinalmoviles.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PartidoRepository @Inject constructor(
    private val partidoRemoteDataSource: PartidoRemoteDataSource,
    private val partidoDao: PartidoDao
) {

    fun fetchPartidos(): Flow<NetworkResult<List<Partido>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = partidoRemoteDataSource.fetchPartidos()
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let {
                    partidoDao.deleteAll(it.map { partido -> partido.toPartidoEntity() })
                    partidoDao.insertAll(it.map { partido -> partido.toPartidoEntity() })
                }
                partidoDao
            }
        }.flowOn(Dispatchers.IO)
    }

    fun addPartido(partido: Partido): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = partidoRemoteDataSource.addPartido(partido)
            emit(result)
            if (result is NetworkResult.Success || result is NetworkResult.Error) {
                partidoDao.addPartido(partido.toPartidoEntity())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun deletePartido(partido: Partido): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = partidoRemoteDataSource.deletePartido(partido.id)
            emit(result)
            if (result is NetworkResult.Success || result is NetworkResult.Error) {
                partidoDao.deletePartido(partido.toPartidoEntity())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun updatePartido(partido: Partido): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = partidoRemoteDataSource.updatePartido(partido)
            emit(result)
            if (result is NetworkResult.Success || result is NetworkResult.Error) {
                partidoDao.updatePartido(partido.id, partido.nombre)
            }
        }.flowOn(Dispatchers.IO)
    }
}