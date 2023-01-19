package com.example.filmflows.data.local.dao

import androidx.room.*
import com.example.filmflows.data.modelo.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies order by vote_average DESC")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

    @Delete
    fun deleteAll(movie: List<MovieEntity>)
}