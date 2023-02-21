package com.example.examenmoviles.ui.screens.hospitales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenmoviles.data.repository.HospitalRepository
import com.example.examenmoviles.domain.modelo.Hospital
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalesViewModel @Inject constructor(
    private val hospitalRepository: HospitalRepository,
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
        }
    }

    private fun getHospitales() {
        viewModelScope.launch {
            hospitalRepository.fetchHospitales().collect() {result->
                _uiState.update { it.copy(hospitales = result) }
            }
        }
    }

    private fun getPacientes(hospital: Hospital) {
        viewModelScope.launch {
            hospitalRepository.fetchHospitales().collect(){result->
                val pacientes = result.find { it.id == hospital.id }?.pacientes ?: emptyList()
                _uiState.update { it.copy(pacientes = pacientes) }
            }
        }
    }
}