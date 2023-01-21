package com.example.filmflows.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmflows.common.Constantes

@Entity(tableName = Constantes.seriesTable)
data class SeriesEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String?,
    val overview: String?,
    val vote_average: Double = 0.0,
    val first_air_date: String?,
    val poster_path: String?,
)