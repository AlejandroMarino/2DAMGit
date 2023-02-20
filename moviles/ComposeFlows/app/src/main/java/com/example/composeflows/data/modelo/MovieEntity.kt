package com.example.composeflows.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composeflows.common.Constantes

@Entity(tableName = Constantes.movieTable)
data class MovieEntity(
    @PrimaryKey
    val id: Int = 0,
    val title: String?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double = 0.0,
    val poster_path: String?,
)