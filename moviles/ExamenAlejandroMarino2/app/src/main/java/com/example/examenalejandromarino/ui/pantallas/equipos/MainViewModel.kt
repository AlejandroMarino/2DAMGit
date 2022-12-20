package com.example.examenalejandromarino.ui.pantallas.equipos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenalejandromarino.domain.usecases.equipo.DeleteEquipo
import com.example.examenalejandromarino.domain.usecases.equipo.GetEquipos
import com.example.examenalejandromarino.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getEquipos: GetEquipos,
    private val deleteEquipo: DeleteEquipo,
) : ViewModel() {

    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    init {
        loadEquipos()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.DeleteEquipo -> {
                deleteEquipo(event.id)
            }
            MainEvent.LoadEquipos -> {
                loadEquipos()
            }
        }
    }

    private fun loadEquipos() {
        viewModelScope.launch {
            _uiState.value = MainState(equipos = getEquipos.invoke(), mensaje = null)
        }
    }

    private fun deleteEquipo(id: Int) {
        viewModelScope.launch {
            if (deleteEquipo.invoke(id)) {
                _uiState.value = MainState(equipos = getEquipos.invoke())
            } else {
                _uiState.value = _uiState.value?.copy(mensaje = "error al borrar")
            }
        }
    }

}