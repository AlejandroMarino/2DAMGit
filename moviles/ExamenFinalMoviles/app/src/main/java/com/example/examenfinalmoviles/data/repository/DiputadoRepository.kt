package com.example.examenfinalmoviles.data.repository

import com.example.examenfinalmoviles.data.remote.DiputadoRemoteDataSource
import com.example.examenfinalmoviles.domain.modelo.Diputado
import com.example.examenfinalmoviles.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class DiputadoRepository @Inject constructor(
    private val diputadoRemoteDataSource: DiputadoRemoteDataSource,
) {

    fun fetchDiputados(): Flow<NetworkResult<List<Diputado>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = diputadoRemoteDataSource.fetchDiputados()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchDiputadosFromPartido(id: UUID): Flow<NetworkResult<List<Diputado>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = diputadoRemoteDataSource.fetchDiputadosFromPartido(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun addDiputado(diputado: Diputado): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = diputadoRemoteDataSource.addDiputado(diputado)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteDiputado(id: UUID): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = diputadoRemoteDataSource.deleteDiputado(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}