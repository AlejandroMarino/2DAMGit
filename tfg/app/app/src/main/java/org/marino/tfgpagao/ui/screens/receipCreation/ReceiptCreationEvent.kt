package org.marino.tfgpagao.ui.screens.receipCreation

import org.marino.tfgpagao.ui.model.MemberReceiptVO


sealed class ReceiptCreationEvent {
    object ErrorCatch : ReceiptCreationEvent()
    class LoadMembersOfGroup(
        val groupId: Int,
        val payerId: Int,
        val receiverId: Int,
        val amount: Double
    ) : ReceiptCreationEvent()

    class ShowOrHidePayerDescription(val index: Int, val descriptionShowed: Boolean) :
        ReceiptCreationEvent()

    class ShowOrHideDebtorDescription(val index: Int, val descriptionShowed: Boolean) :
        ReceiptCreationEvent()

    class ChangePayerSelected(val index: Int, val member: MemberReceiptVO) : ReceiptCreationEvent()
    class ChangeDebtorSelected(val index: Int, val member: MemberReceiptVO) : ReceiptCreationEvent()
    class ChangePayerDescription(val index: Int, val description: String) : ReceiptCreationEvent()
    class ChangeDebtorDescription(val index: Int, val description: String) : ReceiptCreationEvent()
    class ChangePayerAmount(val index: Int, val amount: Double) : ReceiptCreationEvent()
    class ChangeDebtorAmount(val index: Int, val amount: Double) : ReceiptCreationEvent()
    object AddPayer : ReceiptCreationEvent()
    object AddDebtor : ReceiptCreationEvent()
    class DeletePayer(val index: Int) : ReceiptCreationEvent()
    class DeleteDebtor(val index: Int) : ReceiptCreationEvent()
    class ChangeReceiptName(val name: String) : ReceiptCreationEvent()
    class ChangeReceiptDescription(val description: String) : ReceiptCreationEvent()
    class AddReceipt(val goGroupInfo: () -> Unit) : ReceiptCreationEvent()
}