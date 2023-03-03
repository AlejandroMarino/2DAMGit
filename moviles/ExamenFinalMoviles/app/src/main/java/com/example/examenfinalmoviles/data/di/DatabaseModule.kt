package com.example.examenfinalmoviles.data.di

import android.content.Context
import androidx.room.Room
import com.example.examenfinalmoviles.common.Constantes
import com.example.examenfinalmoviles.data.local.AppDatabase
import com.example.examenfinalmoviles.data.local.dao.PartidoDao
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
    fun providePartidoDao(appDatabase: AppDatabase): PartidoDao {
        return appDatabase.partidoDao()
    }

}