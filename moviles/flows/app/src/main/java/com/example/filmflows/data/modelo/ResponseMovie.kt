package com.example.filmflows.data.modelo

data class ResponseMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val releaseDate: String,
    val posterPath: String,
)