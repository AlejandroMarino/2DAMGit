package com.example.composeflows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeflows.common.Constantes
import com.example.composeflows.data.local.dao.MovieDao
import com.example.composeflows.data.local.dao.SeriesDao
import com.example.composeflows.data.modelo.MovieEntity
import com.example.composeflows.data.modelo.SeriesEntity

@Database(entities = [MovieEntity::class, SeriesEntity::class], version = Constantes.version)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
}