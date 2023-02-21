package com.example.composeflows.ui.screens.seriesDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeflows.domain.usecases.series.GetSeries
import com.example.composeflows.utils.NetworkResult
import com.example.composeflows.utils.StringProvider
import com.example.composeflows.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getSeries: GetSeries,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeriesDetailState())
    val uiState: StateFlow<SeriesDetailState> = _uiState

    fun handleEvent(event: SeriesDetailEvent) {
        when (event) {
            is SeriesDetailEvent.GetSeriesDetail -> {
                getSeries(event.id)
            }
        }
    }

    private fun getSeries(id: Int) {
        viewModelScope.launch {
            getSeries.invoke(id).collect { result ->
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message,
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                series = result.data,
                                isLoading = false
                            )
                        }
                    }
                } else {
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                        else -> _uiState.update {
                            it.copy(
                                series = result.data,
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }
}