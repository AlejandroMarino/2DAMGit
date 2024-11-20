package org.marino.tfgpagao.ui.screens.receiptInfo

sealed class ReceiptInfoEvent {
    object ErrorCatch : ReceiptInfoEvent()
    class LoadReceipt(val receiptId: Int) : ReceiptInfoEvent()
}