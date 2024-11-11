package org.marino.tfgpagao.ui.screens.insideGroup.balances

import org.marino.tfgpagao.domain.model.Member

data class BalanceListState(
    val members: List<Member> = emptyList(),
    val transactions: List<Pair<Member, Member>> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)