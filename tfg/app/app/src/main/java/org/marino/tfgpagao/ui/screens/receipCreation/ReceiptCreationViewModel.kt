package org.marino.tfgpagao.ui.screens.receipCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.model.Participation
import org.marino.tfgpagao.domain.model.Receipt
import org.marino.tfgpagao.domain.usecases.member.GetMembersOfGroup
import org.marino.tfgpagao.domain.usecases.receipt.AddReceipt
import org.marino.tfgpagao.ui.model.MemberReceiptVO
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class ReceiptCreationViewModel @Inject constructor(
    private val getMembersOfGroup: GetMembersOfGroup,
    private val addReceipt: AddReceipt,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ReceiptCreationState())
    val state: StateFlow<ReceiptCreationState> = _state.asStateFlow()

    fun handleEvent(event: ReceiptCreationEvent) {
        when (event) {
            ReceiptCreationEvent.ErrorCatch -> {
                errorCatch()
            }

            is ReceiptCreationEvent.LoadMembersOfGroup -> {
                loadMembersOfGroup(event.groupId, event.payerId, event.receiverId, event.amount)
            }

            is ReceiptCreationEvent.ShowOrHidePayerDescription -> {
                showOrHidePayerDescription(event.index, event.descriptionShowed)
            }

            is ReceiptCreationEvent.ChangePayerSelected -> {
                changePayerSelected(event.index, event.member)
            }

            is ReceiptCreationEvent.AddReceipt -> {
                createReceipt(event.goGroupInfo)
            }

            is ReceiptCreationEvent.ChangePayerDescription -> {
                changePayerDescription(event.index, event.description)
            }

            ReceiptCreationEvent.AddPayer -> {
                addPayer()
            }

            is ReceiptCreationEvent.DeletePayer -> {
                deletePayer(event.index)
            }

            is ReceiptCreationEvent.ChangePayerAmount -> {
                changePayerAmount(event.index, event.amount)
            }

            ReceiptCreationEvent.AddDebtor -> {
                addDebtor()
            }

            is ReceiptCreationEvent.ChangeDebtorAmount -> {
                changeDebtorAmount(event.index, event.amount)
            }

            is ReceiptCreationEvent.ChangeDebtorDescription -> {
                changeDebtorDescription(event.index, event.description)
            }

            is ReceiptCreationEvent.ChangeDebtorSelected -> {
                changeDebtorSelected(event.index, event.member)
            }

            is ReceiptCreationEvent.DeleteDebtor -> {
                deleteDebtor(event.index)
            }

            is ReceiptCreationEvent.ShowOrHideDebtorDescription -> {
                showOrHideDebtorDescription(event.index, event.descriptionShowed)
            }

            is ReceiptCreationEvent.ChangeReceiptDescription -> {
                changeReceiptDescription(event.description)
            }

            is ReceiptCreationEvent.ChangeReceiptName -> {
                changeReceiptName(event.name)
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

    private fun loadMembersOfGroup(groupId: Int, payerId: Int, receiverId: Int, amount: Double) {
        viewModelScope.launch {
            getMembersOfGroup.invoke(groupId).collect { result ->
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
                            _state.update {
                                it.copy(
                                    members = members,
                                    isLoading = false,
                                    receipt = it.receipt.copy(groupId = groupId)
                                )
                            }
                            setUpPayers()
                            setUpDebtors()
                            if (payerId > 0 && receiverId > 0 && amount > 0) {
                                setupPredefined(payerId, receiverId, amount)
                            }
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

    private fun setUpPayers() {
        val members = _state.value.members
        val selectedPayers = _state.value.selectedPayers

        val availablePayers = members.filter { member ->
            selectedPayers.none { selected -> selected.name == member.name }
        }.map { member ->
            MemberReceiptVO(name = member.name)
        }
        _state.update { it.copy(availablePayers = availablePayers) }
        if (_state.value.selectedPayers.isEmpty()) {
            addPayer()
        }
    }

    private fun setUpDebtors() {
        val members = _state.value.members
        val selectedDebtors = _state.value.selectedDebtors

        val availableDebtors = members.filter { member ->
            selectedDebtors.none { selected -> selected.name == member.name }
        }.map { member ->
            MemberReceiptVO(name = member.name)
        }
        _state.update { it.copy(availableDebtors = availableDebtors) }
        if (_state.value.selectedDebtors.isEmpty()) {
            addDebtor()
        }
    }

    private fun setupPredefined(payerId: Int, receiverId: Int, amount: Double) {
        val members = _state.value.members
        val payer = members.filter { it.id == payerId }[0]
        val receiver = members.filter { it.id == receiverId }[0]
        if (payer.id == payerId && receiver.id == receiverId) {
            _state.update {
                it.copy(
                    receipt = it.receipt.copy(name = "Debt settlement"),
                    selectedPayers = listOf(MemberReceiptVO(name = payer.name, amount = amount)),
                    selectedDebtors = listOf(MemberReceiptVO(name = receiver.name, amount = amount))
                )
            }
        }
    }

    private fun showOrHidePayerDescription(index: Int, showingDescription: Boolean) {
        _state.update {
            val members = it.selectedPayers.mapIndexed { i, member ->
                if (i == index) {
                    member.copy(showingDescription = showingDescription)
                } else {
                    member
                }
            }
            it.copy(selectedPayers = members)
        }
    }

    private fun showOrHideDebtorDescription(index: Int, showingDescription: Boolean) {
        _state.update {
            val members = it.selectedDebtors.mapIndexed { i, member ->
                if (i == index) {
                    member.copy(showingDescription = showingDescription)
                } else {
                    member
                }
            }
            it.copy(selectedDebtors = members)
        }
    }

    private fun changePayerSelected(index: Int, newMember: MemberReceiptVO) {
        _state.update {
            val availablePayers = it.availablePayers.toMutableList()
            val selectedPayers = it.selectedPayers.mapIndexed { i, member ->
                if (i == index) {
                    availablePayers.remove(newMember)
                    availablePayers.add(
                        member.copy(
                            description = "",
                            amount = 0.0,
                            showingDescription = false
                        )
                    )
                    newMember.copy(
                        description = member.description,
                        amount = member.amount,
                        showingDescription = member.showingDescription
                    )
                } else {
                    member
                }
            }
            it.copy(selectedPayers = selectedPayers, availablePayers = availablePayers)
        }
    }

    private fun changeDebtorSelected(index: Int, newMember: MemberReceiptVO) {
        _state.update {
            val availableDebtors = it.availableDebtors.toMutableList()
            val selectedDebtors = it.selectedDebtors.mapIndexed { i, member ->
                if (i == index) {
                    availableDebtors.remove(newMember)
                    availableDebtors.add(
                        member.copy(
                            description = "",
                            amount = 0.0,
                            showingDescription = false
                        )
                    )
                    newMember.copy(
                        description = member.description,
                        amount = member.amount,
                        showingDescription = member.showingDescription
                    )
                } else {
                    member
                }
            }
            it.copy(selectedDebtors = selectedDebtors, availableDebtors = availableDebtors)
        }
    }

    private fun changePayerDescription(index: Int, description: String) {
        _state.update {
            val selectedPayers = it.selectedPayers.mapIndexed { i, payer ->
                if (i == index) {
                    val member = payer.copy(description = description)
                    member
                } else {
                    payer
                }
            }
            it.copy(selectedPayers = selectedPayers)
        }
    }

    private fun changeDebtorDescription(index: Int, description: String) {
        _state.update {
            val selectedDebtors = it.selectedDebtors.mapIndexed { i, debtor ->
                if (i == index) {
                    val member = debtor.copy(description = description)
                    member
                } else {
                    debtor
                }
            }
            it.copy(selectedDebtors = selectedDebtors)
        }
    }

    private fun changePayerAmount(index: Int, amount: Double) {
        _state.update {
            val selectedPayers = it.selectedPayers.mapIndexed { i, payer ->
                if (i == index) {
                    val member = payer.copy(amount = amount)
                    member
                } else {
                    payer
                }
            }
            it.copy(selectedPayers = selectedPayers)
        }
        updateBalance()
    }

    private fun changeDebtorAmount(index: Int, amount: Double) {
        _state.update {
            val selectedDebtors = it.selectedDebtors.mapIndexed { i, debtor ->
                if (i == index) {
                    val member = debtor.copy(amount = amount)
                    member
                } else {
                    debtor
                }
            }
            it.copy(selectedDebtors = selectedDebtors)
        }
        updateBalance()
    }

    private fun addPayer() {
        _state.update {
            val availablePayers = it.availablePayers.toMutableList()
            val payer = availablePayers.removeAt(0)
            it.copy(selectedPayers = it.selectedPayers + payer, availablePayers = availablePayers)
        }
    }

    private fun addDebtor() {
        _state.update {
            val availableDebtors = it.availableDebtors.toMutableList()
            val debtor = availableDebtors.removeAt(0)
            it.copy(
                selectedDebtors = it.selectedDebtors + debtor,
                availableDebtors = availableDebtors
            )
        }
    }

    private fun deletePayer(index: Int) {
        _state.update {
            val selectedPayers = it.selectedPayers.toMutableList()
            val payer = selectedPayers.removeAt(index).copy(description = "", amount = 0.0)
            it.copy(selectedPayers = selectedPayers, availablePayers = it.availablePayers + payer)
        }
        updateBalance()
    }

    private fun deleteDebtor(index: Int) {
        _state.update {
            val selectedDebtors = it.selectedDebtors.toMutableList()
            val debtor = selectedDebtors.removeAt(index).copy(description = "", amount = 0.0)
            it.copy(
                selectedDebtors = selectedDebtors,
                availableDebtors = it.availableDebtors + debtor
            )
        }
        updateBalance()
    }

    private fun updateBalance() {
        _state.update { state ->
            val amountPaid = state.selectedPayers.sumOf { it.amount }
            val amountDebt = state.selectedDebtors.sumOf { it.amount }
            state.copy(receipt = state.receipt.copy(totalPaid = amountPaid - amountDebt))
        }
    }

    private fun changeReceiptName(name: String) {
        _state.update {
            it.copy(receipt = it.receipt.copy(name = name))
        }
    }

    private fun changeReceiptDescription(description: String) {
        _state.update {
            it.copy(receipt = it.receipt.copy(description = description))
        }
    }

    private fun createReceipt(goGroupInfo: () -> Unit) {
        val receipt = _state.value.receipt
        val balance = receipt.totalPaid ?: 0.0
        val amountPaid = _state.value.selectedDebtors.sumOf { it.amount }

        if (receipt.name.isBlank()) {
            _state.update {
                it.copy(error = "The receipt must have a name")
            }
        } else if (balance < 0 || balance > 0) {
            _state.update {
                it.copy(error = "Balance between amount paid and amount debt has to be 0")
            }
        } else if (amountPaid <= 0) {
            _state.update {
                it.copy(error = "The receipt should contain a payment")
            }
        } else {
            val members = _state.value.members
            val payers = _state.value.selectedPayers
            val debtors = _state.value.selectedDebtors
            val participations = payers.mapNotNull { payer ->
                val member = members.find { it.name == payer.name }
                val description: String = if (payer.description.isBlank()) {
                    ""
                } else {
                    ("Pays: " + payer.description)
                }
                member?.let {
                    Participation(
                        memberId = member.id,
                        description = description,
                        receiptId = 0,
                        pays = payer.amount,
                        debts = 0.0
                    )
                }
            }.toMutableList()


            debtors.forEach { debtor ->
                val member = members.find { it.name == debtor.name }
                val participation = participations.find { it.memberId == member?.id }
                if (participation != null) {
                    val description: String = if (debtor.description.isBlank()) {
                        ""
                    } else if (!participation.description.isNullOrBlank()) {
                        (participation.description + "\nDebts: " + debtor.description)
                    } else {
                        ("Debts: " + debtor.description)
                    }
                    val updatedParticipation = participation.copy(
                        description = description,
                        debts = debtor.amount
                    )
                    val index = participations.indexOf(participation)
                    participations[index] = updatedParticipation
                } else {
                    member?.let {
                        participations.add(
                            Participation(
                                memberId = member.id,
                                receiptId = 0,
                                description = "Debts: " + debtor.description,
                                pays = 0.0,
                                debts = debtor.amount
                            )
                        )
                    }
                }
            }
            addCreatedReceipt(
                receipt = receipt.copy(
                    participations = participations,
                    totalPaid = 0.0
                ),
                goGroupInfo
            )
        }
    }

    private fun addCreatedReceipt(receipt: Receipt, goGroupInfo: () -> Unit) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                addReceipt.invoke(receipt).collect { result ->
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
                            _state.update { it.copy(isLoading = false) }
                            goGroupInfo()
                        }

                        is NetworkResult.SuccessNoData -> {
                            _state.update { it.copy(isLoading = false) }
                            goGroupInfo()
                        }
                    }
                }
            } else {
                _state.update { it.copy(error = "Internet connection needed") }
            }
        }
    }
}