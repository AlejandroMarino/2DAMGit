package com.example.composeflows.ui.screens.movieDetail

import com.example.composeflows.data.modelo.ResponseMovie

data class MovieDetailState(
    val movie: ResponseMovie? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)