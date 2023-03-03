package com.example.examenfinalmoviles.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "partidos")
data class PartidoEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var nombre: String,
)