package com.example.examenmoviles.domain.usecases

import com.example.examenmoviles.data.repository.HospitalRepository
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPacientesDeHospital @Inject constructor() {
    suspend operator fun invoke(hospital: Hospital) : List<Paciente> {
        return hospital.pacientes
    }
}