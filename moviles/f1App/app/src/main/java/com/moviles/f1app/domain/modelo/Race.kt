package com.moviles.f1app.domain.modelo

import java.time.LocalDate

data class Race(
    var id: Int = 0,
    val track: String = "",
    val date: LocalDate = LocalDate.now(),
    val performances: Map<Int, Performance> = emptyMap(),
)