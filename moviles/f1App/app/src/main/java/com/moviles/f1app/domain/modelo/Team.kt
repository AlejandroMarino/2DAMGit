package com.moviles.f1app.domain.modelo

data class Team (
    val id: Int = 0,
    val name: String = "",
    val car: String = "",
    val drivers: List<Driver> = emptyList(),
)