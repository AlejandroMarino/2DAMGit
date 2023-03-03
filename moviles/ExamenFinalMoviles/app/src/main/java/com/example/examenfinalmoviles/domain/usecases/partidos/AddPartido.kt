package com.example.examenfinalmoviles.domain.usecases.partidos

import com.example.examenfinalmoviles.data.repository.PartidoRepository
import com.example.examenfinalmoviles.domain.modelo.Partido
import java.util.*
import javax.inject.Inject

class AddPartido @Inject constructor(private val partidoRepository: PartidoRepository) {
    operator fun invoke(nombre: String) = partidoRepository.addPartido(Partido(id = UUID.randomUUID(),nombre = nombre))
}