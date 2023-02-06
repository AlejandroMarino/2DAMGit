package com.example.compose.data.di

import android.content.Context
import androidx.room.Room
import com.example.compose.data.ProductsDao
import com.example.compose.data.ProductsRoomDatabase
import com.example.compose.data.common.Constantes
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
    ): ProductsRoomDatabase =
        Room.databaseBuilder(context, ProductsRoomDatabase::class.java, Constantes.database)
            .createFromAsset(ruta)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesTeamDao(productsRoomDatabase: ProductsRoomDatabase): ProductsDao =
        productsRoomDatabase.daoProducts()
}