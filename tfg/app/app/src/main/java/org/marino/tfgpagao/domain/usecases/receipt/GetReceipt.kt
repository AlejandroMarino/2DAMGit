package org.marino.tfgpagao.domain.usecases.receipt

import org.marino.tfgpagao.data.repository.ReceiptRepository
import javax.inject.Inject

class GetReceipt @Inject constructor(private val receiptRepository: ReceiptRepository) {
    operator fun invoke(id: Int) = receiptRepository.fetchReceipt(id)
}