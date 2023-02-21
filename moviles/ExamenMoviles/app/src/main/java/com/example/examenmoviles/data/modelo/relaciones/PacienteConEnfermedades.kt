package com.example.examenmoviles.data.modelo.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.examenmoviles.data.modelo.EnfermedadEntity
import com.example.examenmoviles.data.modelo.PacienteEntity

data class PacienteConEnfermedades(
    @Embedded val paciente: PacienteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idPaciente"
    )
    val enfermedades: List<EnfermedadEntity>
)