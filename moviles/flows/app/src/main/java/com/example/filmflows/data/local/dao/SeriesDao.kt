package com.example.filmflows.data.local.dao

import androidx.room.*
import com.example.filmflows.data.modelo.MovieEntity
import com.example.filmflows.data.modelo.SeriesEntity


@Dao
interface SeriesDao {

    @Query("SELECT * FROM series order by vote_average DESC")
    suspend fun getAll(): List<SeriesEntity>

    @Query("SELECT * FROM series WHERE id = :id")
    suspend fun getMovie(id: Int): SeriesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<SeriesEntity>)

    @Delete
    suspend fun deleteAll(movie: List<SeriesEntity>)
}