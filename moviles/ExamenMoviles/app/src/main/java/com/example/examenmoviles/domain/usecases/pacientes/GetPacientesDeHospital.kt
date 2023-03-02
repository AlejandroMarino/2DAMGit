package com.example.examenmoviles.domain.usecases.pacientes

import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente
import javax.inject.Inject

class GetPacientesDeHospital @Inject constructor() {
    operator fun invoke(hospital: Hospital) : List<Paciente> {
        return hospital.pacientes
    }
}