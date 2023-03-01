package com.example.examenmoviles.network.services


import com.example.examenmoviles.common.Constantes
import com.example.examenmoviles.domain.modelo.Hospital
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface HospitalService {

    @GET(Constantes.getHospitales)
    suspend fun getHospitales(): Response<List<Hospital>>

    @DELETE(Constantes.deleteHospital)
    suspend fun deleteHospital(@Path("id") id: UUID): Response<Void>
}