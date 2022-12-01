package com.example.examenalejandromarino.ui.pantallas.addEquipos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenalejandromarino.domain.modelo.Equipo
import com.example.examenalejandromarino.domain.usecases.equipo.AddEquipo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEquiposViewModel @Inject constructor(
    private val addEquipo: AddEquipo
) : ViewModel() {

    private val _uiState = MutableLiveData<AddEquiposState>()
    val uiState: LiveData<AddEquiposState> get() = _uiState

    fun handleEvent(event: AddEquiposEvent) {
        when (event) {
            is AddEquiposEvent.AddEquipo -> {
                addEquipo(event.equipo)
            }
        }
    }

    private fun addEquipo(equipo: Equipo) {
        viewModelScope.launch {
            if (!addEquipo.invoke(equipo)) {
                _uiState.value = AddEquiposState(mensaje = "Error al añadir el equipo")
            } else {
                _uiState.value = AddEquiposState(mensaje = "Equipo añadido correctamente")
            }
        }
    }

}