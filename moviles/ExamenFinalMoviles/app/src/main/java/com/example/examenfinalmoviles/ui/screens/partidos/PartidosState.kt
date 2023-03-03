package com.example.examenfinalmoviles.ui.screens.partidos

import com.example.examenfinalmoviles.domain.modelo.Partido
import java.util.*

data class PartidosState(
    val partido: Partido = Partido(id = UUID.randomUUID(), nombre = ""),
    val partidos: List<Partido> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)