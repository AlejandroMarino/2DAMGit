package com.example.filmflows.domain.modelo

data class Series(
    val id: Int,
    val name: String,
    val overview: String,
    val voteAverage: Double,
    val firstAirDate: String?,
    val posterPath: String?,
)