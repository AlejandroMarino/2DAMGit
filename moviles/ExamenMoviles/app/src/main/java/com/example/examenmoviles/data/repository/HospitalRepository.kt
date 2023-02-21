package com.example.examenmoviles.data.repository

import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.network.services.HospitalService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HospitalRepository @Inject constructor(
    private val hospitalService: HospitalService
) {

    fun fetchHospitales(): Flow<List<Hospital>> {
        return flow {
            val result = hospitalService.getHospitales()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}