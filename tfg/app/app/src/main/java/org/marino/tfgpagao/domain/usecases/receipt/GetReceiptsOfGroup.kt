package org.marino.tfgpagao.domain.usecases.receipt

import org.marino.tfgpagao.data.repository.MemberRepository
import org.marino.tfgpagao.data.repository.ReceiptRepository
import javax.inject.Inject

class GetReceiptsOfGroup @Inject constructor(private val receiptRepository: ReceiptRepository) {
    operator fun invoke(groupId: Int) = receiptRepository.fetchReceiptsOfGroup(groupId)
}