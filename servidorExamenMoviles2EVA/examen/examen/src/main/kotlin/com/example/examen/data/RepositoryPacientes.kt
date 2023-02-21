package com.example.examen.data

import com.example.examen.domain.Enfermedad
import com.example.examen.domain.Hospital
import com.example.examen.domain.Paciente
import org.springframework.stereotype.Repository
import org.springframework.web.context.annotation.ApplicationScope


@Repository
@ApplicationScope
class RepositoryPacientes(val repositoryHospitales: RepositoryHospitales) {


    fun getPacientes() = repositoryHospitales.getHospitales().flatMap { it.pacientes }
    fun updatePacientes(id: String, paciente: Paciente) {

        repositoryHospitales.getHospitales().flatMap { it.pacientes }.find { it.id.toString() == id }?.let {
            it.nombre = paciente.nombre
         }

    }

    fun addEnfermedadPacientes(id: String, enfermedad: Enfermedad) {

            repositoryHospitales.getHospitales().flatMap { it.pacientes }.find { it.id.toString() == id }?.let {
                it.enfermedades.add(enfermedad)
            }

    }


}


