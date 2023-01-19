package com.example.filmflows.data.local.dao

import androidx.room.*
import com.example.filmflows.data.modelo.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies order by vote_average DESC")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun deleteAll(movie: List<MovieEntity>)
}