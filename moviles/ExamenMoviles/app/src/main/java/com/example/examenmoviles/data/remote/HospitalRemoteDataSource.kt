package com.example.examenmoviles.data.remote

import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.network.services.HospitalService
import com.example.examenmoviles.utils.NetworkResult
import javax.inject.Inject

class HospitalRemoteDataSource @Inject constructor(private val hospitalService: HospitalService) :
    BaseApiResponse() {

    suspend fun fetchHospitales(): NetworkResult<List<Hospital>> {
        return safeApiCall(apiCall = { hospitalService.getHospitales() })
    }
}