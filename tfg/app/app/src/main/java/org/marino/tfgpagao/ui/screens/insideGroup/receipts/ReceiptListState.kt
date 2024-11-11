package org.marino.tfgpagao.ui.screens.insideGroup.receipts

import org.marino.tfgpagao.domain.model.Receipt

data class ReceiptListState(
    val receipts: List<Receipt> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)