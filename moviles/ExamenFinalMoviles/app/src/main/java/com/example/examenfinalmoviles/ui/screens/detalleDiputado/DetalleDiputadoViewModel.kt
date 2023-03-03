package com.example.examenfinalmoviles.ui.screens.detalleDiputado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenfinalmoviles.domain.modelo.Diputado
import com.example.examenfinalmoviles.domain.usecases.diputados.GetDiputados
import com.example.examenfinalmoviles.utils.NetworkResult
import com.example.examenfinalmoviles.utils.StringProvider
import com.example.examenfinalmoviles.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetalleDiputadoViewModel @Inject constructor(
    private val getDiputados: GetDiputados,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleDiputadoState())
    val uiState: StateFlow<DetalleDiputadoState> = _uiState

    fun handleEvent(event: DetalleDiputadoEvent) {
        when (event) {
            is DetalleDiputadoEvent.GetDiputado -> {
                getDiputado(event.id)
            }
            DetalleDiputadoEvent.ErrorCatch -> {
                errorCatch()
            }
        }
    }

    private fun errorCatch() {
        _uiState.update {
            it.copy(
                error = "",
            )
        }
    }

    private fun getDiputado(id: UUID) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                getDiputados.invoke().collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message ?: "Error al obtener los datos",
                                    isLoading = false,
                                    diputado = Diputado(
                                        UUID.randomUUID(),
                                        "",
                                        false,
                                        UUID.randomUUID(),
                                        LocalDate.now(),
                                        null,
                                        null
                                    )
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    diputado = result.data?.stream()
                                        ?.filter { diputado -> diputado.id == id }?.findFirst()
                                        ?.get() ?: Diputado(
                                        UUID.randomUUID(),
                                        "",
                                        false,
                                        UUID.randomUUID(),
                                        LocalDate.now(),
                                        null,
                                        null
                                    ),
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.SuccessNoData -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                    }
                }
            } else {
                getDiputados.invoke().collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    diputado = Diputado(
                                        UUID.randomUUID(),
                                        "",
                                        false,
                                        UUID.randomUUID(),
                                        LocalDate.now(),
                                        null,
                                        null
                                    )
                                )
                            }
                        }
                        else -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                diputado = result.data?.stream()
                                    ?.filter { diputado -> diputado.id == id }?.findFirst()?.get()
                                    ?: Diputado(
                                        UUID.randomUUID(),
                                        "",
                                        false,
                                        UUID.randomUUID(),
                                        LocalDate.now(),
                                        null,
                                        null
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}