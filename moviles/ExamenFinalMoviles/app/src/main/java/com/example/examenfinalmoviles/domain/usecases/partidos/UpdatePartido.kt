package com.example.examenfinalmoviles.domain.usecases.partidos

import com.example.examenfinalmoviles.data.repository.PartidoRepository
import com.example.examenfinalmoviles.domain.modelo.Partido
import javax.inject.Inject

class UpdatePartido @Inject constructor(private val partidoRepository: PartidoRepository) {
    operator fun invoke(partido: Partido) = partidoRepository.updatePartido(partido)
}