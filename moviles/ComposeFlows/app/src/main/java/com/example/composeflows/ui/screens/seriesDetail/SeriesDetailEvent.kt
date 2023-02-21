package com.example.composeflows.ui.screens.seriesDetail

sealed class SeriesDetailEvent {
    class GetSeriesDetail(val id: Int) : SeriesDetailEvent()
}