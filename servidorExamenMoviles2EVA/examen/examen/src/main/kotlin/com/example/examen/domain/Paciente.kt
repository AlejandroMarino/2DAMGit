package com.example.examen.domain

import java.util.*

data class Paciente(
    val id: UUID,
    var nombre: String,
    val dni: String,
    val enfermedades: MutableList<Enfermedad> = mutableListOf()
)
