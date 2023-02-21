package com.example.composeflows.data.remote

import com.example.composeflows.data.modelo.ResponseSeries
import com.example.composeflows.data.modelo.toSeries
import com.example.composeflows.domain.modelo.Series
import com.example.composeflows.network.services.SeriesService
import com.example.composeflows.utils.NetworkResult
import javax.inject.Inject

class SeriesRemoteDataSource @Inject constructor(private val seriesService: SeriesService) :
    BaseApiResponse() {

    suspend fun fetchPopularSeries(): NetworkResult<List<Series>> {
        return safeApiCall(apiCall = { seriesService.getPopularSeries() },
            transform = {
                it.results?.map { seriesEntity -> seriesEntity.toSeries() } ?: emptyList()
            })
    }


    suspend fun fetchSeries(id: Int): NetworkResult<ResponseSeries> {
        return safeApiCall(apiCall = { seriesService.getSeries(id) })
    }

}