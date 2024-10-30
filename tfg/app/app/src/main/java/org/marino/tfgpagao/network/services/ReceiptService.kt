package org.marino.tfgpagao.network.services

import org.marino.tfgpagao.domain.model.Receipt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReceiptService {

    @GET("receipts/of_group")
    suspend fun getReceiptsOfGroup(@Query("group") group: Int): Response<List<Receipt>>

    @GET("receipts/{id}")
    suspend fun getReceipt(@Path("id") id: Int): Response<Receipt>

    @GET("receipts/{id}/total_paid")
    suspend fun getReceiptTotalPaid(@Path("id") id: Int): Response<Double>

    @POST("receipts")
    suspend fun add(@Body receipt: Receipt): Response<Void>
}