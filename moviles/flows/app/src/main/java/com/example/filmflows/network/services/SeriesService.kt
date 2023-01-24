package com.example.filmflows.network.services

import com.example.filmflows.common.Constantes
import com.example.filmflows.data.modelo.ResponseListSeries
import com.example.filmflows.data.modelo.ResponseSeries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {

    @GET(Constantes.getPopularSeries)
    suspend fun getPopularSeries(): Response<ResponseListSeries>

    @GET(Constantes.getSeries)
    suspend fun getSeries(@Path(Constantes.seriesId) id: Int): Response<ResponseSeries>
}