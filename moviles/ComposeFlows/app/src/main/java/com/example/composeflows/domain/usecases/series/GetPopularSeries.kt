package com.example.composeflows.domain.usecases.series

import com.example.composeflows.data.repository.SeriesRepository
import javax.inject.Inject

class GetPopularSeries  @Inject constructor(private val seriesRepository: SeriesRepository) {
    fun invoke() = seriesRepository.fetchPopularSeries()
}