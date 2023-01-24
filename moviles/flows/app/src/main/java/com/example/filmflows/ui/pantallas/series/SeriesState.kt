package com.example.filmflows.ui.pantallas.series

import com.example.filmflows.domain.modelo.Series

data class SeriesState (
    val series: List<Series>? = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)