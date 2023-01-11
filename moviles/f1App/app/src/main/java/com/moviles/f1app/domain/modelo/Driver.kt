package com.moviles.f1app.domain.modelo

data class Driver(
    val id: Int = 0,
    val name: String = "",
    val number: Int = -1,
    val performances: Map<Int, Performance> = emptyMap(),
    var idTeam: Int = 0,
)