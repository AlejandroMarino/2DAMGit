package com.example.composeflows.data.repository

import com.example.composeflows.data.local.dao.MovieDao
import com.example.composeflows.data.modelo.ResponseMovie
import com.example.composeflows.data.modelo.toMovie
import com.example.composeflows.data.modelo.toMovieEntity
import com.example.composeflows.data.modelo.toResponseMovie
import com.example.composeflows.data.remote.MovieRemoteDataSource
import com.example.composeflows.domain.modelo.Movie
import com.example.composeflows.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDao: MovieDao
) {
    fun fetchPopularMovies(): Flow<NetworkResult<List<Movie>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = movieRemoteDataSource.fetchPopularMovies()
            emit(result)
            //Cache to database if response is successful
            if (result is NetworkResult.Success) {
                result.data?.let { item ->
                    movieDao.deleteAll(item.map { it.toMovieEntity() })
                    movieDao.insertAll(item.map { it.toMovieEntity() })
                }
            } else {
                emit(fetchPopularMoviesCached())
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchPopularMoviesCached(): NetworkResult<List<Movie>> =
        movieDao.getAll().let { list ->
            NetworkResult.Success(list.map { it.toMovie() })
        }

    fun fetchMovie(id: Int): Flow<NetworkResult<ResponseMovie>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = movieRemoteDataSource.fetchMovie(id)
            emit(result)
            if (result is NetworkResult.Error) {
                emit(fetchMovieCached(id))
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchMovieCached(id: Int): NetworkResult<ResponseMovie> =
        movieDao.getMovie(id).let {
            NetworkResult.Success(it.toMovie().toResponseMovie())
        }

}