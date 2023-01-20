package com.example.filmflows.network.services

import com.example.filmflows.data.modelo.MovieEntity
import com.example.filmflows.data.modelo.ResponseListSeries
import com.example.filmflows.data.modelo.ResponseSeries
import com.example.filmflows.data.modelo.SeriesEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {

    @GET("/3/tv/popular")
    suspend fun getPopularSeries(): Response<ResponseListSeries>

    @GET("/3/tv/{movie_id}")
    suspend fun getSeries(@Path("movie_id") id: Int): Response<ResponseSeries>
}