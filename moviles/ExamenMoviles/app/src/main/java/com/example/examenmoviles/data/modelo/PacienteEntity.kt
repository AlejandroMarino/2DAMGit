package com.example.examenmoviles.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pacientes")
data class PacienteEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val dni: String,
)