package com.example.examenalejandromarino.domain.usecases.componente

import com.example.examenalejandromarino.data.EquipoRepository
import javax.inject.Inject

class GetComponente @Inject constructor(private val equipoRepository: EquipoRepository) {

    suspend fun invoke(nombre: String) = equipoRepository.getComponente(nombre)
}