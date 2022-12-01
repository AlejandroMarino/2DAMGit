package com.example.examenalejandromarino.domain.usecases.componente

import com.example.examenalejandromarino.data.EquipoRepository
import javax.inject.Inject

class GetComponentes @Inject constructor(private val equipoRepository: EquipoRepository) {
    suspend fun invoke(id: Int) = equipoRepository.getComponentes(id)
}