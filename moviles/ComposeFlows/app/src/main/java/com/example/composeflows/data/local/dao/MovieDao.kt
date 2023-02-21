package com.example.composeflows.data.local.dao

import androidx.room.*
import com.example.composeflows.common.Constantes
import com.example.composeflows.data.modelo.MovieEntity

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