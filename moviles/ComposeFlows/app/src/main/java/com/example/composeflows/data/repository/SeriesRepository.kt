package com.example.composeflows.data.repository

import com.example.composeflows.data.local.dao.SeriesDao
import com.example.composeflows.data.modelo.ResponseSeries
import com.example.composeflows.data.modelo.toResponseSeries
import com.example.composeflows.data.modelo.toSeries
import com.example.composeflows.data.modelo.toSeriesEntity
import com.example.composeflows.data.remote.SeriesRemoteDataSource
import com.example.composeflows.domain.modelo.Series
import com.example.composeflows.utils.NetworkResult
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
            emit(NetworkResult.Loading())
            val result = seriesRemoteDataSource.fetchPopularSeries()
            emit(result)
            //Cache to database if response is successful
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    seriesDao.deleteAll(it.map { it.toSeriesEntity() })
                    seriesDao.insertAll(it.map { it.toSeriesEntity() })
                }
            }else {
                emit(fetchPopularSeriesCached())
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchPopularSeriesCached(): NetworkResult<List<Series>> =
        seriesDao.getAll().let { list ->
            NetworkResult.Success(list.map { it.toSeries() })
        }

    fun fetchSeries(id: Int): Flow<NetworkResult<ResponseSeries>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = seriesRemoteDataSource.fetchSeries(id)
            emit(result)
            if (result is NetworkResult.Success) {
                result.data
            }else {
                emit(fetchSeriesCached(id))
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchSeriesCached(id: Int): NetworkResult<ResponseSeries> =
        seriesDao.getSeries(id).let {
            NetworkResult.Success(it.toSeries().toResponseSeries())
        }
}

