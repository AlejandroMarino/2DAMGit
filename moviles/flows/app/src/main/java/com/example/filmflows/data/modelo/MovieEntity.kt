package com.example.filmflows.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int = 0,
    val title: String?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double = 0.0,
    val poster_path: String?,
)