package com.example.examenalejandromarino.ui.pantallas.equipos

import com.example.examenalejandromarino.domain.modelo.Equipo

data class MainState(
    val equipos: List<Equipo> = emptyList(),
    val mensaje: String? = null,
)