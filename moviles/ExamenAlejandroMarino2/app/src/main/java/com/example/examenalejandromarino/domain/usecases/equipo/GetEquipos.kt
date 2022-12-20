package com.example.examenalejandromarino.domain.usecases.equipo

import com.example.examenalejandromarino.data.EquipoRepository
import javax.inject.Inject

class GetEquipos @Inject constructor(private val equipoRepository: EquipoRepository) {
    suspend fun invoke() = equipoRepository.getEquipos()
}