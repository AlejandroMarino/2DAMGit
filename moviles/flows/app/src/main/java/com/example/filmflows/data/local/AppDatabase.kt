package com.example.filmflows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmflows.common.Constantes
import com.example.filmflows.data.local.dao.MovieDao
import com.example.filmflows.data.local.dao.SeriesDao
import com.example.filmflows.data.modelo.MovieEntity
import com.example.filmflows.data.modelo.SeriesEntity

@Database(entities = [MovieEntity::class, SeriesEntity::class], version = Constantes.version)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
}