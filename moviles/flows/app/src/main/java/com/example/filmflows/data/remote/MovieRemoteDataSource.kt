package com.example.filmflows.data.remote

import com.example.filmflows.data.modelo.ResponseMovie
import com.example.filmflows.data.modelo.toMovie
import com.example.filmflows.domain.modelo.Movie
import com.example.filmflows.network.services.MovieService
import com.example.filmflows.utils.NetworkResult
import javax.inject.Inject


class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) :
    BaseApiResponse() {

    suspend fun fetchPopularMovies(): NetworkResult<List<Movie>> {

        return safeApiCall(apiCall = { movieService.getPopularMovies() },
            transform = { it.results?.map { movieEntity -> movieEntity.toMovie() } ?: emptyList() })
    }


    suspend fun fetchMovie(id: Int): NetworkResult<ResponseMovie> {
        return safeApiCall(apiCall = { movieService.getMovie(id) })
    }

}