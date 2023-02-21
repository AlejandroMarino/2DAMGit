package com.example.composeflows.domain.usecases.series

import com.example.composeflows.data.repository.SeriesRepository
import javax.inject.Inject

class GetSeries @Inject constructor(private val seriesRepository: SeriesRepository) {
    fun invoke(id: Int) = seriesRepository.fetchSeries(id)
}