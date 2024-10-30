package org.marino.tfgpagao.data.remote

import org.marino.tfgpagao.domain.model.Receipt
import org.marino.tfgpagao.network.services.ReceiptService
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class ReceiptRemoteDataSource @Inject constructor(private val receiptService: ReceiptService) :
    BaseApiResponse() {

    suspend fun fetchReceiptsOfGroup(groupId: Int): NetworkResult<List<Receipt>> {
        return safeApiCall(apiCall = { receiptService.getReceiptsOfGroup(groupId) })
    }

    suspend fun fetchReceipt(id: Int): NetworkResult<Receipt> {
        return safeApiCall(apiCall = { receiptService.getReceipt(id) })
    }

    suspend fun getTotalPaidOfReceipt(id: Int): NetworkResult<Double> {
        return safeApiCall(apiCall = { receiptService.getReceiptTotalPaid(id)})
    }

    suspend fun addReceipt(receipt: Receipt): NetworkResult<Void> {
        return safeApiCall(apiCall = { receiptService.add(receipt) })
    }
}