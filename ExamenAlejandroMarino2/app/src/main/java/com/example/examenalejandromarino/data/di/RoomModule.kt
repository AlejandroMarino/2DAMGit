package com.example.examenalejandromarino.data.di

import android.content.Context
import androidx.room.Room
import com.example.examenalejandromarino.data.DaoComponente
import com.example.examenalejandromarino.data.DaoEquipo
import com.example.examenalejandromarino.data.EquiposRoomDatabase
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
    @Named("assetDb")
    fun getAssetDB() : String = "database/equipos.db"

    @Provides
    @Singleton
    fun providedDatabase(
        @ApplicationContext context: Context,
        @Named("assetDb") ruta: String
    ) : EquiposRoomDatabase =
        Room.databaseBuilder(context, EquiposRoomDatabase::class.java, "item_database")
        .createFromAsset(ruta)
        .fallbackToDestructiveMigrationFrom(1)
        .build()

    @Provides
    fun providesDaoEquipos(equiposRoomDatabase: EquiposRoomDatabase) : DaoEquipo = equiposRoomDatabase.daoEquipo()

    @Provides
    fun providesDaoComponentes(equiposRoomDatabase: EquiposRoomDatabase) : DaoComponente = equiposRoomDatabase.daoComponente()
}