package com.example.examenfinalmoviles.data.remote

import com.example.examenfinalmoviles.domain.modelo.Partido
import com.example.examenfinalmoviles.network.services.PartidosService
import com.example.examenfinalmoviles.utils.NetworkResult
import com.example.examenmoviles.data.remote.BaseApiResponse
import java.util.*
import javax.inject.Inject

class PartidoRemoteDataSource @Inject constructor(private val partidoService: PartidosService) :
    BaseApiResponse() {

    suspend fun fetchPartidos(): NetworkResult<List<Partido>> {
        return safeApiCall(apiCall = { partidoService.getPartidos() })
    }

    suspend fun addPartido(partido: Partido): NetworkResult<Void> {
        return safeApiCall(apiCall = { partidoService.addPartido(partido) })
    }

    suspend fun deletePartido(id: UUID): NetworkResult<Void> {
        return safeApiCall(apiCall = { partidoService.deletePartido(id) })
    }

    suspend fun updatePartido(partido: Partido): NetworkResult<Void> {
        return safeApiCall(apiCall = { partidoService.updatePartido(partido) })
    }
}