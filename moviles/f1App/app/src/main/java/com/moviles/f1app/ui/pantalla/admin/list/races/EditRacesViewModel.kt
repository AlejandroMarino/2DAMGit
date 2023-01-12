package com.moviles.f1app.ui.pantalla.admin.list.races

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Race
import com.moviles.f1app.domain.usecases.races.AddRace
import com.moviles.f1app.domain.usecases.races.DeleteRace
import com.moviles.f1app.domain.usecases.races.GetRaces
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditRacesViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getRaces: GetRaces,
    private val deleteRace: DeleteRace,
    private val addRace: AddRace,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditRacesState>()
    val uiState: LiveData<EditRacesState> get() = _uiState


    fun handleEvent(event: EditRacesEvent) {
        when (event) {
            EditRacesEvent.LoadRaces -> {
                loadRaces()
            }
            is EditRacesEvent.DeleteRace -> {
                deleteRace(event.race)
            }
            is EditRacesEvent.AddRace -> {
                addRace(event.race)
            }
        }
    }

    private fun loadRaces() {
        viewModelScope.launch {
            _uiState.value = EditRacesState(races = getRaces.invoke(), error = null)
        }
    }

    private fun deleteRace(race: Race) {
        viewModelScope.launch {
            if (
                deleteRace.invoke(race.id)) {
                loadRaces()
                _uiState.value = EditRacesState(races = getRaces.invoke())
            } else {
                _uiState.value =
                    _uiState.value?.copy(error = stringProvider.getString(R.string.error_deleting))
            }
        }
    }

    private fun addRace(race: Race) {
        viewModelScope.launch {
            if (
                addRace.invoke(race)) {
                loadRaces()
                _uiState.value = EditRacesState(races = getRaces.invoke())
            } else {
                _uiState.value =
                    _uiState.value?.copy(error = stringProvider.getString(R.string.error_adding))

            }
        }
    }
}