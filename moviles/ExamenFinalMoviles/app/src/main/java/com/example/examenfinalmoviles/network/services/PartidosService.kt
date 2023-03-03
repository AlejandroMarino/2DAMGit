package com.example.examenfinalmoviles.network.services

import com.example.examenfinalmoviles.common.Constantes
import com.example.examenfinalmoviles.domain.modelo.Partido
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface PartidosService {

    @GET(Constantes.getPartidos)
    suspend fun getPartidos(): Response<List<Partido>>

    @DELETE("/partidos/{id}")
    suspend fun deletePartido(@Path("id") id: UUID): Response<Void>

    @POST("/partidos")
    suspend fun addPartido(@Body partido: Partido): Response<Void>

    @PUT("/partidos")
    suspend fun updatePartido(@Body partido: Partido): Response<Void>
}