package com.example.examenalejandromarino.data.modelo

import com.example.examenalejandromarino.domain.modelo.Componente
import com.example.examenalejandromarino.domain.modelo.Equipo

fun EquipoEntity.toEquipo(): Equipo {
    return Equipo(
        this.id, this.nombre, this.nacionalidad, this.puesto,
    )
}

fun Equipo.toEquipoEntity(): EquipoEntity {
    return EquipoEntity(
        this.id, this.nombre, this.nacionalidad, this.puesto
    )
}

fun ComponenteEntity.toComponente(): Componente {
    return Componente(
        this.tipo, this.nombre,
    )
}

fun Componente.toComponenteEntity(equipoId: Int): ComponenteEntity {
    return ComponenteEntity(
        this.nombre, this.tipo, equipoId
    )
}

fun EquipoWithComponente.toEquipo(): Equipo {
    return Equipo(
        this.equipo.id,
        this.equipo.nombre,
        this.equipo.nacionalidad,
        this.equipo.puesto,
        this.componentes?.map { it.toComponente() }
    )
}

fun Equipo.toEquipoWhithComponente(): EquipoWithComponente {
    return EquipoWithComponente(
        this.toEquipoEntity(), this.componentes?.map { it.toComponenteEntity(this.id) }
    )
}