package com.example.filmflows.data.repository

import com.example.filmflows.data.local.dao.MovieDao
import com.example.filmflows.data.modelo.ResponseMovie
import com.example.filmflows.data.modelo.toMovie
import com.example.filmflows.data.modelo.toMovieEntity
import com.example.filmflows.data.remote.MovieRemoteDataSource
import com.example.filmflows.domain.modelo.Movie
import com.example.filmflows.utils.NetworkResult
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
        movieDao.getAll().let {list->
            NetworkResult.Success(list.map { it.toMovie() } ?: emptyList())
        }

    fun fetchMovie(id: Int): Flow<NetworkResult<ResponseMovie>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(movieRemoteDataSource.fetchMovie(id))
        }.flowOn(Dispatchers.IO)
    }

}