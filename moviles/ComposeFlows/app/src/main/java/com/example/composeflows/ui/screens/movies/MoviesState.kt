package com.example.composeflows.ui.screens.movies

import com.example.composeflows.domain.modelo.Movie

data class MoviesState(
    val movies: List<Movie>? = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)