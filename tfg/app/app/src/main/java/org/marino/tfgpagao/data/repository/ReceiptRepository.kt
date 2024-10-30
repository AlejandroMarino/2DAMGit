package org.marino.tfgpagao.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.marino.tfgpagao.data.remote.ReceiptRemoteDataSource
import org.marino.tfgpagao.domain.model.Receipt
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class ReceiptRepository @Inject constructor(private val receiptRemoteDataSource: ReceiptRemoteDataSource) {

    fun fetchReceiptsOfGroup(groupId: Int): Flow<NetworkResult<List<Receipt>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = receiptRemoteDataSource.fetchReceiptsOfGroup(groupId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchReceipt(id: Int): Flow<NetworkResult<Receipt>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = receiptRemoteDataSource.fetchReceipt(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchTotalPaidOfReceipt(id: Int): Flow<NetworkResult<Double>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = receiptRemoteDataSource.getTotalPaidOfReceipt(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun addReceipt(receipt: Receipt): Flow<NetworkResult<Void>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = receiptRemoteDataSource.addReceipt(receipt)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}