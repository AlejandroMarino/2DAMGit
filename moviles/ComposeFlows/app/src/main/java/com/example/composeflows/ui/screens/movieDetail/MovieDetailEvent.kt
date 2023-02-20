package com.example.composeflows.ui.screens.movieDetail

sealed class MovieDetailEvent {
    class GetMovieDetail(val id: Int) : MovieDetailEvent()
}