package com.example.examenalejandromarino.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.examenalejandromarino.data.modelo.ComponenteEntity
import com.example.examenalejandromarino.data.modelo.EquipoEntity

@Database(entities = [EquipoEntity::class,ComponenteEntity::class], version = 1, exportSchema = true)
abstract class EquiposRoomDatabase : RoomDatabase(){

    abstract fun daoEquipo(): DaoEquipo
}