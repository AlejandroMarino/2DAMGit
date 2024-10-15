package org.marino.tfgpagao.network.services

import org.marino.tfgpagao.domain.model.Receipt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReceiptService {

    @GET("/receipts/of_group")
    suspend fun getReceiptsOfGroup(): Response<List<Receipt>>

    @GET("/receipts/{id}")
    suspend fun getReceipt(@Path("id") id: Int): Response<Receipt>

    @POST("/receipts")
    suspend fun add(@Body receipt: Receipt): Response<Void>
}