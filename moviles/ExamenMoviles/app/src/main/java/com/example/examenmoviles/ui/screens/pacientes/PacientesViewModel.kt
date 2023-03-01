package com.example.examenmoviles.ui.screens.pacientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.domain.usecases.pacientes.GetAllPacientes
import com.example.examenmoviles.utils.NetworkResult
import com.example.examenmoviles.utils.StringProvider
import com.example.examenmoviles.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacientesViewModel @Inject constructor(
    private val getPacientes: GetAllPacientes,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(PacientesState())
    val uiState: StateFlow<PacientesState> = _uiState
    var todosPacientes: List<Paciente> = emptyList()

    fun handleEvent(event: PacientesEvent) {
        when (event) {
            is PacientesEvent.GetPacientes -> {
                getPacientes()
            }
            is PacientesEvent.FilterPacientes -> {
                filterPacientes(event.text)
            }
        }
    }

    private fun filterPacientes(text: String) {
        _uiState.update {
            it.copy(
                pacientes = todosPacientes.filter { paciente ->
                    paciente.nombre.lowercase().contains(text.lowercase())
                }
            )
        }
    }

    private fun getPacientes() {
        viewModelScope.launch {
            getPacientes.invoke().collect { result ->
                if (Utils.hasInternetConnection(stringProvider.context)) {
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
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    pacientes = result.data ?: emptyList(), isLoading = false
                                )
                            }
                            todosPacientes = result.data ?: emptyList()
                        }
                        is NetworkResult.SuccessNoData -> {
                            _uiState.update {
                                it.copy(
                                    pacientes = emptyList(), isLoading = false
                                )
                            }
                            todosPacientes = emptyList()
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
                        else -> {
                            _uiState.update {
                                it.copy(
                                    pacientes = result.data ?: emptyList(), isLoading = false
                                )
                            }
                            todosPacientes = result.data ?: emptyList()
                        }
                    }
                }
            }
        }
    }
}