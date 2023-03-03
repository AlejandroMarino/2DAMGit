package com.example.examenfinalmoviles.domain.usecases.partidos

import com.example.examenfinalmoviles.data.repository.PartidoRepository
import javax.inject.Inject

class GetPartidosRoom @Inject constructor(private val partidoRepository: PartidoRepository) {
    suspend operator fun invoke() = partidoRepository.getPartidos()
}