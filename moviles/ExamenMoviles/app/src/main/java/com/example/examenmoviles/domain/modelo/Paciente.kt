package com.example.examenmoviles.domain.modelo

import java.util.*

data class Paciente(
    val id: UUID,
    var nombre: String,
    val dni: String,
    val enfermedades: MutableList<Enfermedad> = mutableListOf()
)
