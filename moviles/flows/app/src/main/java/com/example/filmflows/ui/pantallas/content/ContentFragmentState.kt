package com.example.filmflows.ui.pantallas.content

import com.example.filmflows.domain.modelo.Movie

data class ContentFragmentState(
    val movies: List<Movie> = emptyList(),
)