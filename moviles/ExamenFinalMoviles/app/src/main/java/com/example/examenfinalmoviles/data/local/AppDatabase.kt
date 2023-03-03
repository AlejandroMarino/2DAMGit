package com.example.examenfinalmoviles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examenfinalmoviles.common.Constantes
import com.example.examenfinalmoviles.data.local.dao.PartidoDao
import com.example.examenfinalmoviles.data.modelo.PartidoEntity

@Database(
    entities = [PartidoEntity::class],
    version = Constantes.version
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun partidoDao(): PartidoDao
}