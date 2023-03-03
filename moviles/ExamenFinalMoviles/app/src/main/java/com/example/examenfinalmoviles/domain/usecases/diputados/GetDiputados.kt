package com.example.examenfinalmoviles.domain.usecases.diputados

import com.example.examenfinalmoviles.data.repository.DiputadoRepository
import javax.inject.Inject

class GetDiputados @Inject constructor(private val diputadoRepository: DiputadoRepository) {
    operator fun invoke() = diputadoRepository.fetchDiputados()
}