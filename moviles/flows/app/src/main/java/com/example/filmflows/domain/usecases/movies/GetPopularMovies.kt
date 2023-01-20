package com.example.filmflows.domain.usecases.movies

import com.example.filmflows.data.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMovies @Inject constructor(private val moviesRepository: MoviesRepository) {
   suspend fun invoke() = moviesRepository.fetchPopularMovies()
}

