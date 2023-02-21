package com.example.composeflows.data.remote

import com.example.composeflows.data.modelo.ResponseMovie
import com.example.composeflows.data.modelo.toMovie
import com.example.composeflows.domain.modelo.Movie
import com.example.composeflows.network.services.MovieService
import com.example.composeflows.utils.NetworkResult
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