package com.example.examenmoviles.data.repository

import com.example.examenmoviles.data.remote.HospitalRemoteDataSource
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HospitalRepository @Inject constructor(
    private val hospitalRemoteDataSource: HospitalRemoteDataSource,
) {

    fun fetchHospitales(): Flow<NetworkResult<List<Hospital>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = hospitalRemoteDataSource.fetchHospitales()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}