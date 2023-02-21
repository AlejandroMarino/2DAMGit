package com.example.examenmoviles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examenmoviles.common.Constantes
import com.example.examenmoviles.data.local.dao.EnfermedadDao
import com.example.examenmoviles.data.local.dao.PacienteDao
import com.example.examenmoviles.data.modelo.EnfermedadEntity
import com.example.examenmoviles.data.modelo.PacienteEntity

@Database(
    entities = [EnfermedadEntity::class, PacienteEntity::class],
    version = Constantes.version
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pacienteDao(): PacienteDao
    abstract fun enfermedadDao(): EnfermedadDao
}