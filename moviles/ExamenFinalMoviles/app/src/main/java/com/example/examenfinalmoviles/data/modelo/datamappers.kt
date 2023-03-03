package com.example.examenfinalmoviles.data.modelo

import com.example.examenfinalmoviles.domain.modelo.Partido

fun PartidoEntity.toPartido(): Partido {
    return Partido(
        id = id,
        nombre = nombre,
    )
}

fun Partido.toPartidoEntity(): PartidoEntity {
    return PartidoEntity(
        id = id,
        nombre = nombre,
    )
}