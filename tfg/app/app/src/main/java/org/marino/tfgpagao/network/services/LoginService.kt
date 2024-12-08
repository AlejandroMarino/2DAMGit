package org.marino.tfgpagao.network.services

import org.marino.tfgpagao.domain.model.User
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("login")
    suspend fun doLogin(@Query("email") email: String, @Query("password") password: String): Response<User>

    @POST("login/register")
    suspend fun register(@Query("email") email: String, @Query("password") password: String): Response<Void>
}