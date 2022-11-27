package com.moviles.appf1teams.data.di

import android.content.Context
import androidx.room.Room
import com.moviles.appf1teams.data.TeamDao
import com.moviles.appf1teams.data.TeamsRoomDatabase
import com.moviles.appf1teams.data.common.Constantes
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
    fun getAssetDB() : String = Constantes.databaseLocation

    @Provides
    @Singleton
    fun providedDatabase(
        @ApplicationContext context: Context,
        @Named(Constantes.assetdb) ruta: String
    ) : TeamsRoomDatabase =
        Room.databaseBuilder(context, TeamsRoomDatabase::class.java, Constantes.database)
        .createFromAsset(ruta)
        .fallbackToDestructiveMigrationFrom(Constantes.destructiveMigration)
        .build()

    @Provides
    fun providesTeamDao(teamsRoomDatabase: TeamsRoomDatabase) : TeamDao = teamsRoomDatabase.teamDao()

}