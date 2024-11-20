package org.marino.tfgpagao.ui.screens.receiptInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.usecases.member.GetMember
import org.marino.tfgpagao.domain.usecases.participation.GetParticipationsOfReceipt
import org.marino.tfgpagao.domain.usecases.receipt.GetReceipt
import org.marino.tfgpagao.ui.model.ParticipationVO
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class ReceiptInfoViewModel @Inject constructor(
    private val getReceipt: GetReceipt,
    private val getParticipationsOfReceipt: GetParticipationsOfReceipt,
    private val getMember: GetMember,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ReceiptInfoState())
    val state: StateFlow<ReceiptInfoState> = _state.asStateFlow()

    fun handleEvent(event: ReceiptInfoEvent) {
        when (event) {
            ReceiptInfoEvent.ErrorCatch -> {
                errorCatch()
            }

            is ReceiptInfoEvent.LoadReceipt -> {
                loadReceipt(event.receiptId)
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

    private fun loadReceipt(idReceipt: Int) {
        viewModelScope.launch {
            getReceipt.invoke(idReceipt).collect { result ->
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
                            val receipt = result.data
                            if (receipt == null) {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "Error while loading receipt"
                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(isLoading = false, receipt = receipt)
                                }
                                setupParticipations(receipt.id)
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

                        else -> {
                            val receipt = result.data
                            if (receipt == null) {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "Error while loading receipt"
                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(isLoading = false, receipt = receipt)
                                }
                                setupParticipations(receipt.id)
                            }
                        }
                    }

                }
            }
        }
    }

    private fun setupParticipations(idReceipt: Int) {
        viewModelScope.launch {
            getParticipationsOfReceipt.invoke(idReceipt).collect { result ->
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
                            val participations = result.data?.map {
                                ParticipationVO(
                                    getMemberName(it.memberId),
                                    it.description,
                                    it.pays,
                                    it.debts
                                )
                            }
                            if (!participations.isNullOrEmpty()) {
                                _state.update {
                                    it.copy(isLoading = false, participations = participations)
                                }
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

                        else -> {
                            val participations = result.data?.map {
                                ParticipationVO(
                                    getMemberName(it.memberId),
                                    it.description,
                                    it.pays,
                                    it.debts
                                )
                            }
                            if (!participations.isNullOrEmpty()) {
                                _state.update {
                                    it.copy(isLoading = false, participations = participations)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getMemberName(memberId: Int): String {
        var memberName = ""
        getMember.invoke(memberId).collect { result ->
            if (result is NetworkResult.Success) {
                memberName = result.data?.name ?: ""
            }
        }
        return memberName
    }
}