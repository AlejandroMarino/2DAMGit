package org.marino.tfgpagao.ui.screens.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.usecases.group.GetGroups
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val getGroups: GetGroups,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(GroupListState())
    val state: StateFlow<GroupListState> = _state.asStateFlow()

    fun handleEvent(event: GroupListEvent) {
        when (event) {
            is GroupListEvent.ErrorCatch -> {
                errorCatch()
            }

            is GroupListEvent.GetGroups -> {
                getGroups()
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

    private fun getGroups() {
        viewModelScope.launch {
            getGroups.invoke().collect { result ->
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
                        is NetworkResult.Success -> _state.update {
                            it.copy(
                                groups = result.data ?: emptyList(), isLoading = false
                            )
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
                            it.copy(groups = result.data ?: emptyList(), isLoading = false)
                        }
                    }
                }
            }
        }
    }
}