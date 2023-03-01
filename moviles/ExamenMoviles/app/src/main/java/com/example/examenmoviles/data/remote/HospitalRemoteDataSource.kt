package com.example.examenmoviles.data.remote

import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.network.services.HospitalService
import com.example.examenmoviles.utils.NetworkResult
import java.util.UUID
import javax.inject.Inject

class HospitalRemoteDataSource @Inject constructor(private val hospitalService: HospitalService) :
    BaseApiResponse() {

    suspend fun fetchHospitales(): NetworkResult<List<Hospital>> {
        return safeApiCall(apiCall = { hospitalService.getHospitales() })
    }

    suspend fun deleteHospital(id: UUID): NetworkResult<Void> {
        return safeApiCall(apiCall = { hospitalService.deleteHospital(id) })
    }
}