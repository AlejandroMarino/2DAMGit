package com.example.filmflows.domain.modelo

data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val releaseDate: String?,
    val posterPath: String?,
)