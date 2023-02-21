package com.example.examenmoviles.domain.usecases

import com.example.examenmoviles.data.repository.HospitalRepository
import javax.inject.Inject

class GetHospitales @Inject constructor(private val hospitalRepository: HospitalRepository) {
    suspend operator fun invoke() = hospitalRepository.fetchHospitales()
}