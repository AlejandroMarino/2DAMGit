package com.example.filmflows.domain.usecases.series

import com.example.filmflows.data.repository.SeriesRepository
import javax.inject.Inject

class GetPopularSeries  @Inject constructor(private val seriesRepository: SeriesRepository) {
    fun invoke() = seriesRepository.fetchPopularSeries()
}