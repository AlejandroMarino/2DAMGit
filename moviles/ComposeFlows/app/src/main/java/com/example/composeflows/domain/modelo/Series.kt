package com.example.composeflows.domain.modelo

data class Series(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val voteAverage: Double = 0.0,
    val firstAirDate: String? = null,
    val posterPath: String? = null,
)