package com.example.examenfinalmoviles.ui.screens.diputados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenfinalmoviles.domain.modelo.Partido
import com.example.examenfinalmoviles.domain.usecases.diputados.GetDiputadosFromPartido
import com.example.examenfinalmoviles.domain.usecases.partidos.GetPartidos
import com.example.examenfinalmoviles.domain.usecases.partidos.GetPartidosRoom
import com.example.examenfinalmoviles.utils.NetworkResult
import com.example.examenfinalmoviles.utils.StringProvider
import com.example.examenfinalmoviles.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiputadosViewModel @Inject constructor(
    private val getPartidos: GetPartidos,
    private val getPartidosRoom: GetPartidosRoom,
    private val getDiputados: GetDiputadosFromPartido,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiputadosState())
    val uiState: StateFlow<DiputadosState> = _uiState

    fun handleEvent(event: DiputadosEvent) {
        when (event) {
            is DiputadosEvent.GetPartidos -> {
                getPartidos()
            }
            is DiputadosEvent.GetDiputados -> {
                getDiputados(event.partido)
            }
            DiputadosEvent.ErrorCatch -> {
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

    private fun getPartidos() {
        viewModelScope.launch {
            val partidos = getPartidosRoom()
            if (partidos.isEmpty()) {
                getPartidosRemote()
            } else {
                _uiState.update {
                    it.copy(
                        partidos = partidos,
                        isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun getPartidosRoom(): List<Partido> {
        return getPartidosRoom.invoke()
    }

    private fun getPartidosRemote() {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                getPartidos.invoke().collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message ?: "",
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                partidos = result.data ?: emptyList(), isLoading = false
                            )
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
                getPartidos.invoke().collect { result ->
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
                                partidos = result.data ?: emptyList(), isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getDiputados(partido: Partido) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                getDiputados.invoke(partido.id).collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = "No hay diputados en este partido",
                                    isLoading = false,
                                    diputados = emptyList()
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                diputados = result.data ?: emptyList(), isLoading = false
                            )
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
                getDiputados.invoke(partido.id).collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    diputados = emptyList()
                                )
                            }
                        }
                        else -> _uiState.update {
                            it.copy(
                                diputados = result.data ?: emptyList(), isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}