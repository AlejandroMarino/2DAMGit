package com.example.examen.domain

import java.util.*

data class Hospital(
    val id: UUID,
    val nombre: String,
    val numeroCamas: Int,
    val direccion: String,
    val pacientes: List<Paciente>
)
