package com.example.examenalejandromarino.domain.usecases.equipo

import com.example.examenalejandromarino.data.EquipoRepository
import com.example.examenalejandromarino.domain.modelo.Equipo
import javax.inject.Inject

class AddEquipo @Inject constructor(private val equipoRepository: EquipoRepository) {

    suspend fun invoke(equipo: Equipo): Boolean {
        return try {
            equipoRepository.addEquipo(equipo)
            true
        } catch (e: Exception) {
            false
        }
    }
}