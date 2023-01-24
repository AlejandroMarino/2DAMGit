package com.example.filmflows.data.local.dao

import androidx.room.*
import com.example.filmflows.common.Constantes
import com.example.filmflows.data.modelo.MovieEntity
import com.example.filmflows.data.modelo.SeriesEntity


@Dao
interface SeriesDao {

    @Query(Constantes.queryGetSeries)
    suspend fun getAll(): List<SeriesEntity>

    @Query(Constantes.queryGetASeries)
    suspend fun getSeries(id: Int): SeriesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<SeriesEntity>)

    @Delete
    suspend fun deleteAll(movie: List<SeriesEntity>)
}