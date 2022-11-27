package com.moviles.appf1teams.domain.modelo

import com.moviles.appf1teams.domain.common.Constantes


data class Team(
    val id: Int = 0,
    val name: String = Constantes.defaultName,
    val performance: Float = Constantes.defaultPerformance,
    val tyre: Int = Constantes.defaultTyre,
    val winner: Boolean = Constantes.defaultWinner,
    val drivers: List<Driver>? = emptyList(),
)


