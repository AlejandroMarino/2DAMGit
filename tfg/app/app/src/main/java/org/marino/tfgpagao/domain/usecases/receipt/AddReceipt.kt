package org.marino.tfgpagao.domain.usecases.receipt

import org.marino.tfgpagao.data.repository.ReceiptRepository
import org.marino.tfgpagao.domain.model.Receipt
import javax.inject.Inject

class AddReceipt @Inject constructor(private val receiptRepository: ReceiptRepository) {
    operator fun invoke(receipt: Receipt) = receiptRepository.addReceipt(receipt)
}