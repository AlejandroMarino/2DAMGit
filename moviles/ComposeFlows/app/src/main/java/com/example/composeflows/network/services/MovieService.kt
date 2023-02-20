package com.example.composeflows.network.services

import com.example.composeflows.common.Constantes
import com.example.composeflows.data.modelo.ResponseMovie
import com.example.composeflows.data.modelo.ResponseMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieService {

    @GET(Constantes.getPopularMovies)
    suspend fun getPopularMovies(): Response<ResponseMovies>

    @GET(Constantes.getMovie)
    suspend fun getMovie(@Path(Constantes.movieId) id: Int): Response<ResponseMovie>
}