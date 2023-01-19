package com.example.filmflows.data.repository

import com.example.filmflows.data.local.dao.SeriesDao
import com.example.filmflows.data.modelo.toSeries
import com.example.filmflows.data.modelo.toSeriesEntity
import com.example.filmflows.data.remote.SeriesRemoteDataSource
import com.example.filmflows.domain.modelo.Series
import com.example.filmflows.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val seriesDao: SeriesDao
) {
    fun fetchPopularSeries(): Flow<NetworkResult<List<Series>>> {
        return flow {
            emit(fetchPospularSeriesCached())
            emit(NetworkResult.Loading())
            val result = seriesRemoteDataSource.fetchPopularSeries()
            emit(result)
            //Cache to database if response is successful
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    seriesDao.deleteAll(it.map { it.toSeriesEntity() })
                    seriesDao.insertAll(it.map { it.toSeriesEntity() })
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchPospularSeriesCached(): NetworkResult<List<Series>> =
        seriesDao.getAll().let { list ->
            NetworkResult.Success(list.map { it.toSeries() } ?: emptyList())
        }

    fun fetchSeries(id: Int): Flow<NetworkResult<Series>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(seriesRemoteDataSource.fetchSeries(id))
        }.flowOn(Dispatchers.IO)
    }
}

