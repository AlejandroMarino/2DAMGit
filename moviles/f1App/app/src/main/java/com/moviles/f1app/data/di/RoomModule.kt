package com.moviles.f1app.data.di

import android.content.Context
import androidx.room.Room
import com.moviles.f1app.data.*
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.dao.DriverDao
import com.moviles.f1app.data.dao.PerformanceDao
import com.moviles.f1app.data.dao.RaceDao
import com.moviles.f1app.data.dao.TeamDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Named(Constantes.assetdb)
    fun getAssetDB(): String = Constantes.databaseLocation

    @Provides
    @Singleton
    fun providedDatabase(
        @ApplicationContext context: Context,
        @Named(Constantes.assetdb) ruta: String
    ): F1RoomDatabase =
        Room.databaseBuilder(context, F1RoomDatabase::class.java, Constantes.database)
            .createFromAsset(ruta)
            .fallbackToDestructiveMigrationFrom(2)
            .build()

    @Provides
    fun providesTeamDao(teamsRoomDatabase: F1RoomDatabase): TeamDao = teamsRoomDatabase.teamDao()

    @Provides
    fun providesDriverDao(teamsRoomDatabase: F1RoomDatabase): DriverDao = teamsRoomDatabase.driverDao()

    @Provides
    fun providesRaceDao(teamsRoomDatabase: F1RoomDatabase): RaceDao = teamsRoomDatabase.raceDao()

    @Provides
    fun providesPerformanceDao(teamsRoomDatabase: F1RoomDatabase): PerformanceDao = teamsRoomDatabase.performanceDao()

}