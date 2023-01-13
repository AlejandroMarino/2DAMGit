package com.moviles.f1app.ui.pantalla.admin.detail.race

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.modelo.Race
import com.moviles.f1app.domain.usecases.performances.AddPerformance
import com.moviles.f1app.domain.usecases.performances.DeletePerformance
import com.moviles.f1app.domain.usecases.performances.GetPerformance
import com.moviles.f1app.domain.usecases.performances.GetRacePerformances
import com.moviles.f1app.domain.usecases.races.AddRace
import com.moviles.f1app.domain.usecases.races.GetRace
import com.moviles.f1app.domain.usecases.races.GetRaceByTrack
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
    private val getRaceByTrack: GetRaceByTrack,
    private val updateRace: UpdateRace,
    private val getPerformance: GetPerformance,
    private val getRacePerformances: GetRacePerformances,
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
                val rc = getRaceByTrack.invoke(race.track)
                _uiState.value = EditRaceState(
                    race = rc,
                    message = stringProvider.getString(R.string.added),
                    performances = getRacePerformances.invoke(rc.id)
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
                performances = getRacePerformances.invoke(id)
            )
        }
    }

    private fun updateRace(race: Race) {
        viewModelScope.launch {
            race.id = _uiState.value?.race?.id ?: 0
            if (updateRace.invoke(race)) {
                _uiState.value = _uiState.value?.copy(
                    race = getRace.invoke(race.id),
                    message = stringProvider.getString(R.string.updated),
                    performances = getRacePerformances.invoke(race.id)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_updating)
                )
            }
        }
    }

    private fun deletePerformance(performance: PerformanceWithObjects) {
        viewModelScope.launch {
            val per = getPerformance.invoke(performance.driver.id, performance.race.id)
            if (deletePerformance.invoke(per)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.deleted),
                    race = getRace.invoke(performance.race.id),
                    performances = getRacePerformances.invoke(performance.race.id)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_deleting)
                )
            }
        }
    }

    private fun addPerformance(performance: PerformanceWithObjects) {
        viewModelScope.launch {
            val per = getPerformance.invoke(performance.driver.id, performance.race.id)
            if (addPerformance.invoke(per)) {
                _uiState.value = _uiState.value?.copy(
                    race = getRace.invoke(performance.race.id),
                    performances = getRacePerformances.invoke(performance.race.id)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }
}