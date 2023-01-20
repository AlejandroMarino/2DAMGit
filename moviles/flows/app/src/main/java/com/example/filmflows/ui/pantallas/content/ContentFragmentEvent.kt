package com.example.filmflows.ui.pantallas.content

sealed interface ContentFragmentEvent {
    object GetPopularMovies : ContentFragmentEvent
}