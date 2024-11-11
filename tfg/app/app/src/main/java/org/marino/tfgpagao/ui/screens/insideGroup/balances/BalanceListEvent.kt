package org.marino.tfgpagao.ui.screens.insideGroup.balances

sealed class BalanceListEvent {
    object ErrorCatch : BalanceListEvent()
    class GetMembers(val idGroup: Int) : BalanceListEvent()
}