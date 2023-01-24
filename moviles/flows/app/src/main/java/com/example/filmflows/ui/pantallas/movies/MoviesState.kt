package com.example.filmflows.ui.pantallas.movies

import com.example.filmflows.domain.modelo.Movie

data class MoviesState(
    val movies: List<Movie>? = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)