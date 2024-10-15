package org.marino.tfgpagao.domain.model

data class Participation (
    val memberId: Int,
    val receiptId: Int,
    val description: String?,
    val pays: Double?,
    val debts: Double?,
)