package com.example.compose2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose2.data.ProductsDao
import com.example.compose2.data.common.Constantes
import com.example.compose2.data.modelo.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = Constantes.version,
    exportSchema = true
)
abstract class ProductsRoomDatabase : RoomDatabase(){

    abstract fun daoProducts(): ProductsDao
}