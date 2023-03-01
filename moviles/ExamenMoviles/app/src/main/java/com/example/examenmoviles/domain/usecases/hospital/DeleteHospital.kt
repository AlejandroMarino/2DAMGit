package com.example.examenmoviles.domain.usecases.hospital

import com.example.examenmoviles.data.repository.HospitalRepository
import com.example.examenmoviles.domain.modelo.Hospital
import javax.inject.Inject

class DeleteHospital @Inject constructor(private val hospitalRepository: HospitalRepository) {

    suspend operator fun invoke(hospital: Hospital) = hospitalRepository.deleteHospital(hospital.id)
}