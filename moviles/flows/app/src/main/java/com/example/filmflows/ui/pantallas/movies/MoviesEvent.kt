package com.example.filmflows.ui.pantallas.movies

sealed interface MoviesEvent {
    object GetPopularMovies : MoviesEvent
    object ErrorCaught: MoviesEvent
}