package com.example.filmflows.domain.modelo

data class Movie (
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val voteAverage: Double = 0.0,
    val releaseDate: String? = null,
    val posterPath: String? = null,
)