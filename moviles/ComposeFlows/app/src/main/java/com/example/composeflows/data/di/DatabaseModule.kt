package com.example.composeflows.data.di

import android.content.Context
import androidx.room.Room
import com.example.composeflows.common.Constantes
import com.example.composeflows.data.local.AppDatabase
import com.example.composeflows.data.local.dao.MovieDao
import com.example.composeflows.data.local.dao.SeriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constantes.appDb
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    fun provideSeriesDao(appDatabase: AppDatabase): SeriesDao {
        return appDatabase.seriesDao()
    }
}