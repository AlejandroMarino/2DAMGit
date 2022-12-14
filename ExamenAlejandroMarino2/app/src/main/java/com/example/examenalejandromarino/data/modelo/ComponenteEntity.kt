package com.example.examenalejandromarino.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "componentes",
    foreignKeys = [
        ForeignKey(
            entity = EquipoEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_equipo"]
        )
    ]
)
data class ComponenteEntity(
    @ColumnInfo(name = "nombre")
    @PrimaryKey(autoGenerate = false)
    val nombre: String,
    @ColumnInfo(name = "tipo")
    val tipo: String,
    @ColumnInfo(name = "id_equipo")
    val id_equipo: Int = 0,
)