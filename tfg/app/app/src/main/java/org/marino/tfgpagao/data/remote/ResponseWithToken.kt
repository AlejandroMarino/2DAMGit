package org.marino.tfgpagao.data.remote

data class ResponseWithToken<T>(
    val body: T?,
    val token: String?
)