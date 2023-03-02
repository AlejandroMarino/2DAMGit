package com.example.examenmoviles.ui.screens.detallePaciente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.domain.usecases.pacientes.AddEnfermedad
import com.example.examenmoviles.domain.usecases.pacientes.GetPaciente
import com.example.examenmoviles.domain.usecases.pacientes.UpdatePaciente
import com.example.examenmoviles.utils.NetworkResult
import com.example.examenmoviles.utils.StringProvider
import com.example.examenmoviles.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetallePacienteViewModel @Inject constructor(
    private val getPaciente: GetPaciente,
    private val addEnfermedad: AddEnfermedad,
    private val updatePaciente: UpdatePaciente,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetallePacienteState())
    val uiState: StateFlow<DetallePacienteState> = _uiState

    fun handleEvent(event: DetallePacienteEvent) {
        when (event) {
            is DetallePacienteEvent.GetPaciente -> {
                getPaciente(event.id)
            }
            is DetallePacienteEvent.AddEnfermedad -> {
                addEnfermedad(event.enfermedad)
            }
            is DetallePacienteEvent.UpdatePaciente -> {
                updatePaciente(event.paciente)
            }
            is DetallePacienteEvent.OnValuePacienteChange -> {
                onValuePacienteChange(event.paciente)
            }
            is DetallePacienteEvent.OnValueEnfermedadChange -> {
                onValueEnfermedadChange(event.enfermedad)
            }
            DetallePacienteEvent.ErrorCatch -> {
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

    private fun onValuePacienteChange(paciente: Paciente) {
        _uiState.update {
            it.copy(paciente = paciente)
        }
    }

    private fun onValueEnfermedadChange(enfermedad: Enfermedad) {
        _uiState.update {
            it.copy(enfermedad = enfermedad)
        }
    }


    private fun getPaciente(id: String) {
        viewModelScope.launch {
            val paciente = getPaciente.invoke(UUID.fromString(id))
            _uiState.value = DetallePacienteState(paciente = paciente)
        }
    }

    private fun addEnfermedad(enfermedad: Enfermedad) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(stringProvider.context)) {
                addEnfermedad.invoke(uiState.value.paciente, enfermedad).collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message ?: "Error al añadir",
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        else -> {
                            getPaciente(uiState.value.paciente.id.toString())
                        }
                    }
                }
            } else {
                addEnfermedad.invoke(uiState.value.paciente, enfermedad).collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                        else -> {
                            getPaciente(uiState.value.paciente.id.toString())
                        }
                    }
                }
            }
        }
    }

    private fun updatePaciente(paciente: Paciente) {
        viewModelScope.launch {
            if (paciente.nombre.isBlank()) {
                _uiState.update {
                    it.copy(
                        error = "El nombre no puede estar vacio",
                        isLoading = false
                    )
                }
            } else {
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    updatePaciente.invoke(paciente).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message ?: "Error al actualizar",
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            else -> {
                                getPaciente(uiState.value.paciente.id.toString())
                            }
                        }
                    }
                } else {
                    updatePaciente.invoke(paciente).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                    )
                                }
                            }
                            else -> {
                                getPaciente(uiState.value.paciente.id.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}