package com.example.filmflows.data.local.dao

import androidx.room.*
import com.example.filmflows.common.Constantes
import com.example.filmflows.data.modelo.MovieEntity

@Dao
interface MovieDao {

    @Query(Constantes.queryGetMovies)
    suspend fun getAll(): List<MovieEntity>

    @Query(Constantes.queryGetMovie)
    suspend fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun deleteAll(movie: List<MovieEntity>)
}