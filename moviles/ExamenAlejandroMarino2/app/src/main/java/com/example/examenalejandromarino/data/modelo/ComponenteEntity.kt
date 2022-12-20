package com.example.examenalejandromarino.data.modelo

import androidx.room.*
import com.example.examenalejandromarino.domain.modelo.Tipo

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
    val tipo: Tipo,
    @ColumnInfo(name = "id_equipo")
    val id_equipo: Int,
)