package com.example.filmflows.ui.pantallas.series

sealed interface SeriesEvent {
    object GetPopularSeries : SeriesEvent
}