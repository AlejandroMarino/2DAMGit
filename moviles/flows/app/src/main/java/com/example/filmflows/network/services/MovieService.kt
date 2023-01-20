package com.example.filmflows.network.services

import com.example.filmflows.data.modelo.MovieEntity
import com.example.filmflows.data.modelo.ResponseMovie
import com.example.filmflows.data.modelo.ResponseMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<ResponseMovies>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int): Response<ResponseMovie>
}