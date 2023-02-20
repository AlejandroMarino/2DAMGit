package com.example.composeflows.network.services

import com.example.composeflows.common.Constantes
import com.example.composeflows.data.modelo.ResponseListSeries
import com.example.composeflows.data.modelo.ResponseSeries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {

    @GET(Constantes.getPopularSeries)
    suspend fun getPopularSeries(): Response<ResponseListSeries>

    @GET(Constantes.getSeries)
    suspend fun getSeries(@Path(Constantes.seriesId) id: Int): Response<ResponseSeries>
}