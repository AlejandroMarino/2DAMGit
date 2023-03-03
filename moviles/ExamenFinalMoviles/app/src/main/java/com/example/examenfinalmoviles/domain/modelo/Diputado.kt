package com.example.examenfinalmoviles.domain.modelo

import java.time.LocalDate
import java.util.*

data class Diputado(
    val id: UUID,
    val nombre: String,
    val corrupto: Boolean,
    val idPartido: UUID,
    val fechaEntradaCongreso: LocalDate,
    val causasConfirmadas: List<String>? = null,
    val causasSupuestas: List<String>? = null,
)
