package com.example.filmflows.data.modelo

data class ResponseSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val voteAverage: Double,
    val firstAirDate: String,
    val posterPath: String,
)