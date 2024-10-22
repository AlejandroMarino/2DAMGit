package org.marino.tfgpagao.network.services

import org.marino.tfgpagao.domain.model.Member
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberService {

    @GET("/members/of_group")
    suspend fun getMembersOfGroup(@Query("group") group: Int): Response<List<Member>>

    @GET("/members/{id}")
    suspend fun getMember(@Path("id") id: Int): Response<Member>

    @GET("/members/{id}/balance")
    suspend fun getMemberBalance(@Path("id") id: Int): Response<Double>

    @POST("/members")
    suspend fun add(@Body member: Member): Response<Void>
}