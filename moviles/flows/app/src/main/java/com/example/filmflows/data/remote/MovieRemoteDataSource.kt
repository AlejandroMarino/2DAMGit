package com.example.filmflows.data.remote

import com.example.filmflows.data.modelo.toMovie
import com.example.filmflows.domain.modelo.Movie
import com.example.filmflows.network.services.MovieService
import com.example.filmflows.utils.NetworkResult
import javax.inject.Inject

/**
 * fetches data from remote source
 */
class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) :
    BaseApiResponse() {

    suspend fun fetchPopularMovies(): NetworkResult<List<Movie>> {

        return safeApiCall(apiCall = { movieService.getPopularMovies() },
            transform = { it.map { movieEntity -> movieEntity.toMovie() } })
    }


    suspend fun fetchMovie(id: Int): NetworkResult<Movie> {

        return safeApiCall(apiCall = { movieService.getMovie(id) }, transform = { it.toMovie() })

    }

//    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
//        return try {
//            println("I'm working in thread ${Thread.currentThread().name}")
//            val result = request.invoke()
//            if (result.isSuccessful) {
//                return Result.success(result.body())
//            } else {
//                val errorResponse = ErrorUtils.parseError(result, retrofit)
//                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
//            }
//        } catch (e: Throwable) {
//            Result.error("Unknown Error", null)
//        }
//    }
}