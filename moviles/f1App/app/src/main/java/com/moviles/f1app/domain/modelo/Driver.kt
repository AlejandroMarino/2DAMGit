package com.moviles.f1app.domain.modelo

data class Driver(
    val id: Int = 0,
    val name: String,
    val number: Int,
    val performances: Map<Int, Performance> = emptyMap(),
    val idTeam: Int = 0,
)