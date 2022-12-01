package com.example.examenalejandromarino.data.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class EquipoWithComponente(
    @Embedded val equipo: EquipoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_equipo"
    )
    val componentes: List<ComponenteEntity>?
)