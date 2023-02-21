package com.example.examenmoviles.data.di

import android.content.Context
import androidx.room.Room
import com.example.examenmoviles.common.Constantes
import com.example.examenmoviles.data.local.dao.EnfermedadDao
import com.example.examenmoviles.data.local.dao.PacienteDao
import com.example.examenmoviles.data.local.AppDatabase
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
    fun providePacienteDao(appDatabase: AppDatabase): PacienteDao {
        return appDatabase.pacienteDao()
    }

    @Provides
    fun provideEnfermedadDao(appDatabase: AppDatabase): EnfermedadDao {
        return appDatabase.enfermedadDao()
    }
}