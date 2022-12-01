package com.example.examenalejandromarino.domain.modelo

data class Equipo(
    val id: Int = 0,
    val nombre:String = "",
    val nacionalidad: String = "",
    val puesto: Int = 0,
    val componentes: List<Componente>? = emptyList(),
)