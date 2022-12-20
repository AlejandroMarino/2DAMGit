package com.example.examenalejandromarino.domain.usecases.equipo

import com.example.examenalejandromarino.data.EquipoRepository
import javax.inject.Inject

class DeleteEquipo @Inject constructor(private val equipoRepository: EquipoRepository) {

    suspend fun invoke(id: Int): Boolean {
        val equipo = equipoRepository.getEquipo(id)
        if (equipo.componentes == null || equipo.componentes.isEmpty()) {
            return false
        } else {
            return try {
                equipoRepository.deleteEquipo(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}