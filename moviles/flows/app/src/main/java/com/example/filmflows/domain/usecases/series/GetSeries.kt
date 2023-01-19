package com.example.filmflows.domain.usecases.series

import com.example.filmflows.data.repository.SeriesRepository
import javax.inject.Inject

class GetSeries @Inject constructor(private val seriesRepository: SeriesRepository) {
    operator fun invoke(id: Int) = seriesRepository.fetchSeries(id)
}