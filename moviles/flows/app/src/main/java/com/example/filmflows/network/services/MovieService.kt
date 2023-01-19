package com.example.filmflows.network.services

import com.example.filmflows.data.modelo.MovieEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<List<MovieEntity>>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int): Response<MovieEntity>
}