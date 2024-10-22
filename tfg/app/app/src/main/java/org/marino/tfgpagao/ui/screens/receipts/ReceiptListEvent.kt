package org.marino.tfgpagao.ui.screens.receipts

sealed class ReceiptListEvent {
    object ErrorCatch: ReceiptListEvent()
    class GetReceipts(val idGroup: Int) : ReceiptListEvent()
}