package com.example.examenalejandromarino.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "equipos",
    indices = [Index(value = ["nombre"], unique = true),
        Index(value = ["puesto"], unique = true)],
)
data class EquipoEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "nacionalidad")
    val nacionalidad: String,
    @ColumnInfo(name = "puesto")
    val puesto: Int
)