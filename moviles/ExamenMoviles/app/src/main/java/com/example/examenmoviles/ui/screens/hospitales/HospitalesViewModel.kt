package com.example.examenmoviles.ui.screens.hospitales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.usecases.hospital.DeleteHospital
import com.example.examenmoviles.domain.usecases.hospital.GetHospitales
import com.example.examenmoviles.domain.usecases.pacientes.GetPacientesDeHospital
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
class HospitalesViewModel @Inject constructor(
    private val getHospitales: GetHospitales,
    private val getPacientesDeHospital: GetPacientesDeHospital,
    private val deleteHospital: DeleteHospital,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(HospitalesState())
    val uiState: StateFlow<HospitalesState> = _uiState

    fun handleEvent(event: HospitalesEvent) {
        when (event) {
            is HospitalesEvent.GetHospitales -> {
                getHospitales()
            }
            is HospitalesEvent.GetPacientes -> {
                getPacientes(event.hospital)
            }
            is HospitalesEvent.DeleteHospital -> {
                deleteHospital(event.hospital)
            }
        }
    }

    private fun getHospitales() {
        viewModelScope.launch {
            getHospitales.invoke().collect { result ->
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
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                hospitales = result.data?: emptyList(), isLoading = false
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
                                hospitales = result.data?: emptyList(), isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getPacientes(hospital: Hospital) {
        viewModelScope.launch {
            val pacientes = getPacientesDeHospital.invoke(hospital)
            _uiState.update {
                it.copy(
                    pacientes = pacientes
                )
            }
        }
    }

    private fun deleteHospital(hospital: Hospital) {
        viewModelScope.launch {
            deleteHospital.invoke(hospital).collect{ result ->
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message ?: "Error al eliminar",
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        else -> {
                            getHospitales()
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
                           getHospitales()
                        }
                    }
                }
            }
        }
    }
}