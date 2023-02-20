package com.example.composeflows.ui.screens.movies

sealed interface MoviesEvent {
    object GetPopularMovies : MoviesEvent
    object ErrorCaught: MoviesEvent
}