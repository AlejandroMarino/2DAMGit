package org.marino.tfgpagao.ui.screens.receipCreation

import org.marino.tfgpagao.domain.model.Member
import org.marino.tfgpagao.domain.model.Receipt
import org.marino.tfgpagao.ui.model.MemberReceiptVO

data class ReceiptCreationState(
    val members: List<Member> = emptyList(),
    val availablePayers: List<MemberReceiptVO> = emptyList(),
    val availableDebtors: List<MemberReceiptVO> = emptyList(),
    val selectedPayers: List<MemberReceiptVO> = emptyList(),
    val selectedDebtors: List<MemberReceiptVO> = emptyList(),
    val receipt: Receipt = Receipt(
        id = 0,
        name = "",
        description = "",
        totalPaid = 0.0,
        groupId = 0,
        participations = emptyList()
    ),
    val error: String? = null,
    val isLoading: Boolean = false
)