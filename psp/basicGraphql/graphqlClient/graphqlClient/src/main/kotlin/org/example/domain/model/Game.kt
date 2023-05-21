package org.example.domain.model

import java.time.LocalDate

data class Game(
    val id: Int = 0,
    val name: String,
    val description: String? = null,
    val releaseDate: LocalDate? = null,
    val shop: Shop,
)