package com.example.filmflows.data.remote

import com.example.filmflows.data.modelo.ResponseSeries
import com.example.filmflows.data.modelo.toSeries
import com.example.filmflows.domain.modelo.Series
import com.example.filmflows.network.services.SeriesService
import com.example.filmflows.utils.NetworkResult
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