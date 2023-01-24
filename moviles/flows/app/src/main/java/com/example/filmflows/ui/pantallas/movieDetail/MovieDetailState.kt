package com.example.filmflows.ui.pantallas.movieDetail

import com.example.filmflows.data.modelo.ResponseMovie

data class MovieDetailState(
    val movie: ResponseMovie? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)