package com.example.examenmoviles.data.modelo

import com.example.examenmoviles.data.modelo.relaciones.PacienteConEnfermedades
import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente

fun PacienteConEnfermedades.toPaciente(): Paciente {
    return Paciente(
        id = this.paciente.id,
        nombre = this.paciente.nombre,
        dni = this.paciente.dni,
        enfermedades = this.enfermedades.map { it.toEnfermedad() } as MutableList<Enfermedad>
    )
}

fun Paciente.toPacienteEntity(): PacienteEntity {
    return PacienteEntity(
        id = this.id,
        nombre = this.nombre,
        dni = this.dni,
    )
}

fun Paciente.toPacienteConEnfermedades(): PacienteConEnfermedades {
    return PacienteConEnfermedades(
        paciente = this.toPacienteEntity(),
        enfermedades = this.enfermedades.map { it.toEnfermedadEntity() } as MutableList<EnfermedadEntity>
    )
}

fun PacienteEntity.toPaciente(): Paciente {
    return Paciente(
        id = this.id,
        nombre = this.nombre,
        dni = this.dni,
    )
}

fun EnfermedadEntity.toEnfermedad(): Enfermedad {
    return Enfermedad(
        nombre = this.nombre
    )
}

fun Enfermedad.toEnfermedadEntity(): EnfermedadEntity {
    return EnfermedadEntity(
        nombre = this.nombre
    )
}