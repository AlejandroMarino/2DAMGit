package com.moviles.f1app.ui.pantalla.admin.detail.race

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Race
import com.moviles.f1app.domain.modelo.Team
import com.moviles.f1app.domain.usecases.races.AddRace
import com.moviles.f1app.domain.usecases.races.GetRace
import com.moviles.f1app.domain.usecases.races.UpdateRace
import com.moviles.f1app.ui.pantalla.admin.detail.team.EditTeamState
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRaceViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addRace: AddRace,
    private val getRace: GetRace,
    private val updateRace: UpdateRace,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditRaceState>()
    val uiState: LiveData<EditRaceState> get() = _uiState

    fun handleEvent(event: EditRaceEvent) {
        when (event) {
            is EditRaceEvent.AddRace -> {
                addRace(event.race)
            }
            is EditRaceEvent.GetRace -> {
                getRace(event.id)
            }
            is EditRaceEvent.UpdateRace -> {
                updateRace(event.race)
            }
        }
    }

    private fun addRace(race: Race) {
        viewModelScope.launch {
            if (addRace.invoke(race)) {
                _uiState.value = EditRaceState(
                    getRace.invoke(race.id),
                    stringProvider.getString(R.string.added)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }

    private fun getRace(id: Int) {
        viewModelScope.launch {
            _uiState.value = EditRaceState(race = getRace.invoke(id))
        }
    }

    private fun updateRace(race: Race) {
        viewModelScope.launch {
            if (updateRace.invoke(race)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.updated)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_updating)
                )
            }
        }
    }
}