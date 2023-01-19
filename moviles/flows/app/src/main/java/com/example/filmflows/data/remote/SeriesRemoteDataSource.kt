package com.example.filmflows.data.remote

import com.example.filmflows.data.modelo.toSeries
import com.example.filmflows.domain.modelo.Series
import com.example.filmflows.network.services.SeriesService
import com.example.filmflows.utils.NetworkResult
import javax.inject.Inject

class SeriesRemoteDataSource @Inject constructor(private val seriesService: SeriesService) :
    BaseApiResponse() {

    suspend fun fetchPopularMovies(): NetworkResult<List<Series>> {

        return safeApiCall(apiCall = { seriesService.getPopularSeries() },
            transform = { it.map { seriesEntity -> seriesEntity.toSeries() } })
    }


    suspend fun fetchMovie(id: Int): NetworkResult<Series> {

        return safeApiCall(apiCall = { seriesService.getSeries(id) }, transform = { it.toSeries() })

    }

}