package com.example.examenfinalmoviles.domain.usecases.partidos

import com.example.examenfinalmoviles.data.repository.PartidoRepository
import javax.inject.Inject

class GetPartidos @Inject constructor(private val partidoRepository: PartidoRepository) {
    operator fun invoke() = partidoRepository.fetchPartidos()
}