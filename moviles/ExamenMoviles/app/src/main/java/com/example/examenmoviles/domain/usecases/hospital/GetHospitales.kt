package com.example.examenmoviles.domain.usecases.hospital

import com.example.examenmoviles.data.repository.HospitalRepository
import javax.inject.Inject

class GetHospitales @Inject constructor(private val hospitalRepository: HospitalRepository) {
    operator fun invoke() = hospitalRepository.fetchHospitales()
}