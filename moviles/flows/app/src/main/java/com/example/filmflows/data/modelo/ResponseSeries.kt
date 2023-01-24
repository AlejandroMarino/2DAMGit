package com.example.filmflows.data.modelo

data class ResponseSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val vote_average: Double,
    val first_air_date: String,
    val poster_path: String,
)