package com.example.examenalejandromarino.domain.usecases.equipo

import com.example.examenalejandromarino.data.EquipoRepository
import javax.inject.Inject

class GetEquipo  @Inject constructor(private val equipoRepository: EquipoRepository) {
    suspend fun invoke(id: Int) = equipoRepository.getEquipo(id)
}