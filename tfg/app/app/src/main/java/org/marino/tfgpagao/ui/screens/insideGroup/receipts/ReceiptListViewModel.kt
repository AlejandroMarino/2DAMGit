package org.marino.tfgpagao.ui.screens.insideGroup.receipts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.usecases.receipt.GetReceiptsOfGroup
import org.marino.tfgpagao.domain.usecases.receipt.GetTotalPaidOfReceipt
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class ReceiptListViewModel @Inject constructor(
    private val getReceiptsOfGroup: GetReceiptsOfGroup,
    private val getTotalPaidOfReceipt: GetTotalPaidOfReceipt,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ReceiptListState())
    val state: StateFlow<ReceiptListState> = _state.asStateFlow()

    fun handleEvent(event: ReceiptListEvent) {
        when (event) {
            ReceiptListEvent.ErrorCatch -> {
                errorCatch()
            }

            is ReceiptListEvent.GetReceipts -> {
                getReceipts(event.idGroup)
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

    private fun getReceipts(idGroup: Int) {
        viewModelScope.launch {
            getReceiptsOfGroup.invoke(idGroup).collect { result ->
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
                            val receipts = result.data ?: emptyList()
                            _state.update {
                                it.copy(
                                    receipts = receipts, isLoading = false
                                )
                            }
                            receipts.forEach { receipt -> setTotalPaidToReceipt(receipt.id) }
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
                            it.copy(receipts = result.data ?: emptyList(), isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun setTotalPaidToReceipt(receiptId: Int) {
        viewModelScope.launch {
            getTotalPaidOfReceipt.invoke(receiptId).collect { result ->
                if (result is NetworkResult.Success) {
                    val totalPaid = result.data as Double
                    _state.update {
                        val receipts = it.receipts.map { receipt ->
                            if (receipt.id == receiptId) receipt.copy(totalPaid = totalPaid)
                            else receipt
                        }
                        it.copy(receipts = receipts)
                    }
                }
            }
        }
    }
}