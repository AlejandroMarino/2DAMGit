package com.example.composeflows.ui.screens.series

sealed interface SeriesEvent {
    object GetPopularSeries : SeriesEvent
}