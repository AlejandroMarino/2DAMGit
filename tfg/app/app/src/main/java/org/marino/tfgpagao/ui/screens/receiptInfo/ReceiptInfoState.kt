package org.marino.tfgpagao.ui.screens.receiptInfo

import org.marino.tfgpagao.domain.model.Receipt
import org.marino.tfgpagao.ui.model.ParticipationVO

data class ReceiptInfoState(
    val receipt: Receipt = Receipt(0, "", "", 0, emptyList(), 0.0),
    val participations: List<ParticipationVO> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)