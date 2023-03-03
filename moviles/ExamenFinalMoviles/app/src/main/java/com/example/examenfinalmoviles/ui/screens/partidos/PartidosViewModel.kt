package com.example.examenfinalmoviles.ui.screens.partidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenfinalmoviles.domain.modelo.Partido
import com.example.examenfinalmoviles.domain.usecases.partidos.*
import com.example.examenfinalmoviles.utils.NetworkResult
import com.example.examenfinalmoviles.utils.StringProvider
import com.example.examenfinalmoviles.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PartidosViewModel @Inject constructor(
    private val getPartidos: GetPartidos,
    private val deletePartido: DeletePartido,
    private val updatePartido: UpdatePartido,
    private val addPartido: AddPartido,
    private val getPartidosRoom: GetPartidosRoom,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(PartidosState())
    val uiState: StateFlow<PartidosState> = _uiState

    fun handleEvent(event: PartidosEvent) {
        when (event) {
            is PartidosEvent.GetPartidos -> {
                getPartidos()
            }
            is PartidosEvent.AddPartido -> {
                addPartido(event.nombre)
            }
            is PartidosEvent.DeletePartido -> {
                deletePartido(event.partido)
            }
            is PartidosEvent.UpdatePartido -> {
                updatePartido(event.nombre)
            }
            PartidosEvent.ErrorCatch -> {
                errorCatch()
            }
            is PartidosEvent.GetPartido -> {
                getPartido(event.partido)
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

    private fun getPartido(partido: Partido) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    partido = partido
                )
            }
        }
    }

    private fun deletePartido(partido: Partido) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                deletePartido.invoke(partido).collect { result ->
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
                        else -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    partido = Partido(UUID.randomUUID(), "")
                                )
                            }
                            getPartidosRemote()
                        }
                    }
                }
            } else {
                deletePartido.invoke(partido).collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                        else -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    partido = Partido(UUID.randomUUID(), "")
                                )
                            }
                            getPartidosRemote()
                        }
                    }
                }
            }
        }
    }

    private fun updatePartido(nombre: String) {
        viewModelScope.launch {
            if (nombre.isEmpty()) {
                _uiState.update {
                    it.copy(
                        error = "El nombre no puede estar vacio",

                        )
                }
            } else if (_uiState.value.partido.nombre.isBlank()) {
                _uiState.update {
                    it.copy(
                        error = "No se ha seleccionado un partido",
                    )
                }
            } else {
                val partido = Partido(
                    id = _uiState.value.partido.id,
                    nombre = nombre
                )
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    updatePartido.invoke(partido).collect { result ->
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
                            else -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        partido = Partido(UUID.randomUUID(), "")
                                    )
                                }
                                getPartidosRemote()
                            }
                        }
                    }
                } else {
                    updatePartido.invoke(partido).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                    )
                                }
                            }
                            else -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        partido = Partido(UUID.randomUUID(), "")
                                    )
                                }
                                getPartidosRemote()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addPartido(nombre: String) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                addPartido.invoke(nombre).collect { result ->
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
                        else -> {
                            getPartidosRemote()
                            _uiState.update {
                                it.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            } else {
                addPartido.invoke(nombre).collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                        else -> {
                            getPartidosRemote()
                            _uiState.update {
                                it.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}