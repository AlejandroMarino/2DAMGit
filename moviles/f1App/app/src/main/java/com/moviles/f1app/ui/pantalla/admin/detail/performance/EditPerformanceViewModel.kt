package com.moviles.f1app.ui.pantalla.admin.detail.performance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.usecases.drivers.GetAllDrivers
import com.moviles.f1app.domain.usecases.drivers.GetDriverByName
import com.moviles.f1app.domain.usecases.performances.AddPerformance
import com.moviles.f1app.domain.usecases.performances.GetPerformance
import com.moviles.f1app.domain.usecases.performances.UpdatePerformance
import com.moviles.f1app.domain.usecases.races.GetRaceByTrack
import com.moviles.f1app.domain.usecases.races.GetRaces
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPerformanceViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getAllDrivers: GetAllDrivers,
    private val getDriverByName: GetDriverByName,
    private val getRaces: GetRaces,
    private val getRaceByTrack: GetRaceByTrack,
    private val getPerformance: GetPerformance,
    private val getPerformanceWithObjects: PerformanceWithObjects,
    private val addPerformance: AddPerformance,
    private val updatePerformance: UpdatePerformance,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditPerformanceState>()
    val uiState: LiveData<EditPerformanceState> get() = _uiState

    fun handleEvent(event: EditPerformanceEvent) {
        when (event) {
            EditPerformanceEvent.GetData -> {
                getData()
            }
            is EditPerformanceEvent.GetPerformance -> {
                getPerformance(event.idDriver, event.idRace)
            }
            is EditPerformanceEvent.AddPerformance -> {
                addPerformance(event.performance, event.driverName, event.trackName)
            }
            is EditPerformanceEvent.UpdatePerformance -> {
                updatePerformance(event.performance, event.driverName, event.trackName)
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            _uiState.value =
                EditPerformanceState(drivers = getAllDrivers.invoke(), races = getRaces.invoke())
        }
    }

    private fun getPerformance(idDriver: Int, idRace: Int) {
        viewModelScope.launch {
            _uiState.value =
                EditPerformanceState(performance = getPerformance.invoke(idDriver, idRace))
        }
    }

    private fun addPerformance(performance: Performance, driverName: String, trackName: String) {
        viewModelScope.launch {
            performance.idDriver = getDriverByName.invoke(driverName).id
            performance.idRace = getRaceByTrack.invoke(trackName).id
            if (addPerformance.invoke(performance)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(com.moviles.f1app.R.string.added)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(com.moviles.f1app.R.string.error_adding)
                )
            }
        }
    }

    private fun updatePerformance(performance: Performance, driverName: String, trackName: String) {
        viewModelScope.launch {
            performance.idDriver = getDriverByName.invoke(driverName).id
            performance.idRace = getRaceByTrack.invoke(trackName).id
            if (updatePerformance.invoke(performance)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(com.moviles.f1app.R.string.updated)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(com.moviles.f1app.R.string.error_updating)
                )
            }
        }
    }
}