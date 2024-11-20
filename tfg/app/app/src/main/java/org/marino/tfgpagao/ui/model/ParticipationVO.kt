package org.marino.tfgpagao.ui.model

data class ParticipationVO(
    val memberName: String = "",
    val description: String? = "",
    val pays: Double = 0.0,
    val debts: Double = 0.0
)