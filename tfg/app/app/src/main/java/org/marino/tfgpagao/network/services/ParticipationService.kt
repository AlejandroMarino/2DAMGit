package org.marino.tfgpagao.network.services

import org.marino.tfgpagao.domain.model.Participation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ParticipationService {

    @GET("/participations/of_receipt")
    suspend fun getParticipationsOfReceipt(): Response<List<Participation>>

    @GET("/participations")
    suspend fun getParticipation(
        @Query("member") member: Int,
        @Query("receipt") receipt: Int
    ): Response<Participation>

    @POST("/participations")
    suspend fun add(@Body participation: Participation): Response<Void>
}