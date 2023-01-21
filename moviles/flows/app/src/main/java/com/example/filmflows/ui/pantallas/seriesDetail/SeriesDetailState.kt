package com.example.filmflows.ui.pantallas.seriesDetail

import com.example.filmflows.data.modelo.ResponseSeries

data class SeriesDetailState(
    val series: ResponseSeries? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)