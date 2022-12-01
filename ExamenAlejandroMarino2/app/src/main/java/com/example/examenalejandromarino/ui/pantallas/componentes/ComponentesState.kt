package com.example.examenalejandromarino.ui.pantallas.componentes

import com.example.examenalejandromarino.domain.modelo.Componente

data class ComponentesState (
    val componentes: List<Componente> = emptyList(),
    val mensaje: String? = null,
        )