package com.example.examenmoviles.network.services

import com.example.examenmoviles.common.Constantes
import com.example.examenmoviles.domain.modelo.Hospital
import retrofit2.Response
import retrofit2.http.GET

interface HospitalService {

    @GET(Constantes.getHospitales)
    suspend fun getHospitales(): Response<List<Hospital>>
}