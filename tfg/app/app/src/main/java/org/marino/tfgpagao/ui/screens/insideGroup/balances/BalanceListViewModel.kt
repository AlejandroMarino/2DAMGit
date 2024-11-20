package org.marino.tfgpagao.ui.screens.insideGroup.balances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.model.Member
import org.marino.tfgpagao.domain.usecases.member.GetBalanceOfMember
import org.marino.tfgpagao.domain.usecases.member.GetMembersOfGroup
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class BalanceListViewModel @Inject constructor(
    private val getMembersOfGroup: GetMembersOfGroup,
    private val getBalanceOfMember: GetBalanceOfMember,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(BalanceListState())
    val state: StateFlow<BalanceListState> = _state.asStateFlow()

    fun handleEvent(event: BalanceListEvent) {
        when (event) {
            BalanceListEvent.ErrorCatch -> {
                errorCatch()
            }

            is BalanceListEvent.GetMembers -> {
                getMembers(event.idGroup)
            }
        }
    }

    private fun errorCatch() {
        _state.update {
            it.copy(
                error = "",
            )
        }
    }

    private fun getMembers(idGroup: Int) {
        viewModelScope.launch {
            getMembersOfGroup.invoke(idGroup).collect { result ->
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    when (result) {
                        is NetworkResult.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.message ?: "",
                                    isLoading = false
                                )
                            }
                        }

                        is NetworkResult.Loading -> _state.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> {
                            val members = result.data ?: emptyList()

                            val updatedMembers = members.toMutableList()

                            updatedMembers.forEachIndexed { index, member ->
                                val balance = fetchBalanceOfMember(member.id)
                                updatedMembers[index] = member.copy(balance = balance)
                            }

                            _state.update {
                                it.copy(members = updatedMembers, isLoading = false)
                            }

                            updateTransactions()
                        }

                        is NetworkResult.SuccessNoData -> _state.update {
                            it.copy(isLoading = false)
                        }
                    }
                } else {
                    when (result) {
                        is NetworkResult.Error -> _state.update {
                            it.copy(isLoading = false, error = "No internet connection")
                        }

                        else -> _state.update {
                            it.copy(members = result.data ?: emptyList(), isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchBalanceOfMember(memberId: Int): Double {
        var balance = 0.0

        getBalanceOfMember.invoke(memberId).collect { result ->
            if (result is NetworkResult.Success) {
                balance = result.data as Double
            }
        }

        return balance
    }

    private fun updateTransactions() {
        val members = _state.value.members
        val transactions = createTransactions(members)
        _state.update { it.copy(transactions = transactions) }
    }

    private fun createTransactions(members: List<Member>): List<Pair<Member, Member>> {
        val payers = members.filter { it.balance != null && it.balance < 0 }.sortedBy { it.balance }
            .toMutableList()
        val receivers = members.filter { it.balance != null && it.balance > 0 }
            .sortedByDescending { it.balance }.toMutableList()

        val transactions = mutableListOf<Pair<Member, Member>>()

        var payIndex = 0
        var receiveIndex = 0

        while (payIndex < payers.size && receiveIndex < receivers.size) {
            val payer = payers[payIndex]
            val receiver = receivers[receiveIndex]
            if (payer.balance != null && receiver.balance != null) {
                val amount = minOf(-payer.balance, receiver.balance)

                transactions.add(
                    Pair(
                        payer.copy(balance = -amount),
                        receiver.copy(balance = amount)
                    )
                )

                payers[payIndex] = payer.copy(balance = payer.balance + amount)
                receivers[receiveIndex] = receiver.copy(balance = receiver.balance - amount)

                if (payers[payIndex].balance == 0.0) payIndex++
                if (receivers[receiveIndex].balance == 0.0) receiveIndex++
            }
        }

        return transactions
    }
}