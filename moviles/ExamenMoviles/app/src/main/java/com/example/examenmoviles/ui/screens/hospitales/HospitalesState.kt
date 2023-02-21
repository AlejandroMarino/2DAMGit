package com.example.examenmoviles.ui.screens.hospitales

import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente

data class HospitalesState(
    val hospitales: List<Hospital> = emptyList(),
    val pacientes: List<Paciente> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)