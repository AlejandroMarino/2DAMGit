package com.example.examenfinalmoviles.ui.screens.diputados

import com.example.examenfinalmoviles.domain.modelo.Diputado
import com.example.examenfinalmoviles.domain.modelo.Partido

data class DiputadosState(
    val partidos: List<Partido> = emptyList(),
    val diputados: List<Diputado> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)