package com.example.filmflows.ui.pantallas.seriesDetail

sealed class SeriesDetailEvent {
    class GetSeriesDetail(val id: Int) : SeriesDetailEvent()
}