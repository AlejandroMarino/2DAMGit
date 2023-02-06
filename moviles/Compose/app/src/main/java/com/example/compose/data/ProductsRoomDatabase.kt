package com.example.compose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose.data.common.Constantes
import com.example.compose.data.modelo.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = Constantes.version,
    exportSchema = false
)
abstract class ProductsRoomDatabase : RoomDatabase(){

    abstract fun daoProducts(): ProductsDao
}