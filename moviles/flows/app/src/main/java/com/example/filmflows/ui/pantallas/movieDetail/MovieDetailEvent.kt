package com.example.filmflows.ui.pantallas.movieDetail

sealed class MovieDetailEvent {
    class GetMovieDetail(val id: Int) : MovieDetailEvent()
}