package com.example.composeflows.ui.screens.series

import com.example.composeflows.domain.modelo.Series

data class SeriesState(
    val series: List<Series>? = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)