package com.example.examenmoviles.data.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "enfermedades",
    foreignKeys = [
        ForeignKey(
            entity = PacienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["idPaciente"]
        )
    ])
data class EnfermedadEntity(
    @PrimaryKey
    val nombre: String,
    val idPaciente: UUID = UUID.randomUUID(),
)
