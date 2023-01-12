package com.moviles.f1app.ui.pantalla.admin.detail.race

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.Race
import com.moviles.f1app.domain.usecases.performances.AddPerformance
import com.moviles.f1app.domain.usecases.performances.DeletePerformance
import com.moviles.f1app.domain.usecases.performances.GetRacePerformances
import com.moviles.f1app.domain.usecases.races.AddRace
import com.moviles.f1app.domain.usecases.races.GetRace
import com.moviles.f1app.domain.usecases.races.UpdateRace
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
    private val deletePerformance: DeletePerformance,
    private val addPerformance: AddPerformance,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditRaceState>()
    val uiState: LiveData<EditRaceState> get() = _uiState

    fun handleEvent(event: EditRaceEvent) {
        when (event) {
            is EditRaceEvent.AddRace -> {
                addRace(event.race)
            }
            is EditRaceEvent.GetData -> {
                getData(event.id)
            }
            is EditRaceEvent.UpdateRace -> {
                updateRace(event.race)
            }
            is EditRaceEvent.DeletePerformance -> {
                deletePerformance(event.performance)
            }
            is EditRaceEvent.AddPerformance -> {
                addPerformance(event.performance)
            }
        }
    }

    private fun addRace(race: Race) {
        viewModelScope.launch {
            if (addRace.invoke(race)) {
                _uiState.value = EditRaceState(
                    message = stringProvider.getString(R.string.added)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }

    private fun getData(id: Int) {
        viewModelScope.launch {
            _uiState.value = EditRaceState(
                race = getRace.invoke(id),
            )
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

    private fun deletePerformance(performance: Performance) {
        viewModelScope.launch {
            if (deletePerformance.invoke(performance)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.deleted),
                    race = getRace.invoke(performance.idRace)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_deleting)
                )
            }
        }
    }

    private fun addPerformance(performance: Performance) {
        viewModelScope.launch {
            if (addPerformance.invoke(performance)) {
                _uiState.value = _uiState.value?.copy(
                    race = getRace.invoke(performance.idRace)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }
}