package org.marino.tfgpagao.network.services

import org.marino.tfgpagao.domain.model.Group
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupService {

    @GET("/groups")
    suspend fun getGroups(): Response<List<Group>>

    @GET("/groups/{id}")
    suspend fun getGroup(@Path("id") id: Int): Response<Group>

    @POST("/groups")
    suspend fun add(@Body group: Group): Response<Void>
}