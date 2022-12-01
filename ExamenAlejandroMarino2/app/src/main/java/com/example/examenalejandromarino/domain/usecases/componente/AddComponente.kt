package com.example.examenalejandromarino.domain.usecases.componente

import com.example.examenalejandromarino.data.EquipoRepository
import com.example.examenalejandromarino.domain.modelo.Componente
import javax.inject.Inject

class AddComponente @Inject constructor(private val equipoRepository: EquipoRepository) {

    suspend fun invoke(id: Int, componente: Componente): Boolean {
        return try {
            equipoRepository.addComponente(id, componente)
            true
        } catch (e: Exception) {
            false
        }
    }
}