package com.example.examenfinalmoviles.network.services

import com.example.examenfinalmoviles.common.Constantes
import com.example.examenfinalmoviles.domain.modelo.Diputado
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface DiputadosService {

    @GET(Constantes.getDiputados)
    suspend fun getDiputados(): Response<List<Diputado>>

    @GET("/diputados/{id}")
    suspend fun getDiputadosFromPartido(@Path("id") id: UUID): Response<List<Diputado>>

    @DELETE("/diputados/{id}")
    suspend fun deleteDiputado(@Path("id") id: UUID): Response<Void>

    @POST("/diputados")
    suspend fun addDiputado(@Body diputado: Diputado): Response<Void>
}