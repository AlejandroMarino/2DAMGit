package org.marino.tfgpagao.ui.screens.insideGroup.receipts

sealed class ReceiptListEvent {
    object ErrorCatch: ReceiptListEvent()
    class GetReceipts(val idGroup: Int) : ReceiptListEvent()
}