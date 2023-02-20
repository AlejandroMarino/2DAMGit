package com.example.composeflows.domain.usecases.movies

import com.example.composeflows.data.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMovies @Inject constructor(private val moviesRepository: MoviesRepository) {
    fun invoke() = moviesRepository.fetchPopularMovies()
}

