package com.example.examenfinalmoviles.data.remote

import com.example.examenfinalmoviles.domain.modelo.Diputado
import com.example.examenfinalmoviles.network.services.DiputadosService
import com.example.examenfinalmoviles.utils.NetworkResult
import com.example.examenmoviles.data.remote.BaseApiResponse
import java.util.*
import javax.inject.Inject

class DiputadoRemoteDataSource @Inject constructor(private val diputadoService: DiputadosService) :
    BaseApiResponse() {

    suspend fun fetchDiputados(): NetworkResult<List<Diputado>> {
        return safeApiCall(apiCall = { diputadoService.getDiputados() })
    }

    suspend fun fetchDiputadosFromPartido(id: UUID): NetworkResult<List<Diputado>> {
        return safeApiCall(apiCall = { diputadoService.getDiputadosFromPartido(id) })
    }

    suspend fun addDiputado(diputado: Diputado): NetworkResult<Void> {
        return safeApiCall(apiCall = { diputadoService.addDiputado(diputado) })
    }

    suspend fun deleteDiputado(id: UUID): NetworkResult<Void> {
        return safeApiCall(apiCall = { diputadoService.deleteDiputado(id) })
    }
}
