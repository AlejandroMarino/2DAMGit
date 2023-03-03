package com.example.examenfinalmoviles.domain.usecases.diputados

import com.example.examenfinalmoviles.data.repository.DiputadoRepository
import java.util.*
import javax.inject.Inject

class GetDiputadosFromPartido @Inject constructor(private val diputadoRepository: DiputadoRepository) {
    operator fun invoke(idPartido: UUID) = diputadoRepository.fetchDiputadosFromPartido(idPartido)
}