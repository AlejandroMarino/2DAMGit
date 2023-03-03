package com.example.examenfinalmoviles.ui.screens.detalleDiputado

import com.example.examenfinalmoviles.domain.modelo.Diputado
import java.time.LocalDate
import java.util.*

data class DetalleDiputadoState(
    val diputado: Diputado = Diputado(
        UUID.randomUUID(),
        "",
        false,
        UUID.randomUUID(),
        LocalDate.now(),
        null,
        null
    ),
    val isLoading: Boolean = false,
    val error: String = ""
)