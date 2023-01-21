package com.example.filmflows.data.modelo

data class ResponseMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val vote_average: Double,
    val release_date: String,
    val poster_path: String,
)