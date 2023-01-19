package com.example.filmflows.domain.usecases.movies

import com.example.filmflows.data.repository.MoviesRepository
import javax.inject.Inject

class GetMovie @Inject constructor(private val moviesRepository: MoviesRepository) {
    operator fun invoke(id: Int) = moviesRepository.fetchMovie(id)
}