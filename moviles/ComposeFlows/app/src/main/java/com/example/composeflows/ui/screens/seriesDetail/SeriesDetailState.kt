package com.example.composeflows.ui.screens.seriesDetail

import com.example.composeflows.data.modelo.ResponseSeries

data class SeriesDetailState(
    val series: ResponseSeries? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)