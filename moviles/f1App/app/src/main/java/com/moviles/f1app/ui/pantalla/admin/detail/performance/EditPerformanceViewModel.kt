package com.moviles.f1app.ui.pantalla.admin.detail.performance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.usecases.drivers.GetAllDrivers
import com.moviles.f1app.domain.usecases.drivers.GetDriver
import com.moviles.f1app.domain.usecases.drivers.GetDriverByName
import com.moviles.f1app.domain.usecases.performances.AddPerformance
import com.moviles.f1app.domain.usecases.performances.GetPerformanceWithObjects
import com.moviles.f1app.domain.usecases.performances.GetRacePerformances
import com.moviles.f1app.domain.usecases.performances.UpdatePerformance
import com.moviles.f1app.domain.usecases.races.GetRace
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
    private val getDriver: GetDriver,
    private val getRace: GetRace,
    private val getRaces: GetRaces,
    private val getRaceByTrack: GetRaceByTrack,
    private val getPerformanceWO: GetPerformanceWithObjects,
    private val getRacePerformances: GetRacePerformances,
    private val addPerformance: AddPerformance,
    private val updatePerformance: UpdatePerformance,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditPerformanceState>()
    val uiState: LiveData<EditPerformanceState> get() = _uiState

    fun handleEvent(event: EditPerformanceEvent) {
        when (event) {
            is EditPerformanceEvent.GetData -> {
                getData(event.idDriver, event.idRace)
            }
            is EditPerformanceEvent.GetPerformance -> {
                getPerformance(event.driverName, event.trackName)
            }
            is EditPerformanceEvent.AddPerformance -> {
                addPerformance(event.performance, event.driverName, event.trackName)
            }
            is EditPerformanceEvent.UpdatePerformance -> {
                updatePerformance(event.performance, event.driverName, event.trackName)
            }
            is EditPerformanceEvent.GetDriverName -> {
                getDriverName(event.idDriver)
            }
            is EditPerformanceEvent.GetTrackName -> {
                getTrackName(event.idRace)
            }
            is EditPerformanceEvent.GetPerformanceByNames -> {
                getPerformanceByNames(event.driverName, event.trackName)
            }
        }
    }

    private fun getData(idDriver: Int, idRace: Int) {
        viewModelScope.launch {
            if (idDriver != 0 && idRace == 0) {
                _uiState.value =
                    EditPerformanceState(
                        drivers = getAllDrivers.invoke(),
                        races = getRaces.invoke(),
                        driverName = getDriver.invoke(idDriver).name
                    )
            } else if (idRace != 0 && idDriver == 0) {
                _uiState.value =
                    EditPerformanceState(
                        drivers = getAllDrivers.invoke(),
                        races = getRaces.invoke(),
                        trackName = getRace.invoke(idRace).track
                    )
            } else if (idDriver != 0) {
                _uiState.value =
                    EditPerformanceState(
                        drivers = getAllDrivers.invoke(),
                        races = getRaces.invoke(),
                        driverName = getDriver.invoke(idDriver).name,
                        trackName = getRace.invoke(idRace).track,
                        performance = getPerformanceWO.invoke(idDriver, idRace)
                    )
            } else {
                _uiState.value =
                    EditPerformanceState(drivers = getAllDrivers.invoke(), races = getRaces.invoke())
            }
        }
    }

    private fun getPerformance(driver: String, track: String) {
        viewModelScope.launch {
            val raceId = getRaceByTrack.invoke(track).id
            val racePerformances = getRacePerformances.invoke(raceId)
            val performance = racePerformances.find {
                it.race.track == track && it.driver.name == driver
            }
            if (performance != null){
                _uiState.value =
                    _uiState.value?.copy(
                        performance = getPerformanceWO.invoke(performance.driver.id, performance.race.id),
                        driverName = driver,
                        trackName = track
                    )
            } else {
                _uiState.value =
                    _uiState.value?.copy(
                        performance = PerformanceWithObjects(),
                        driverName = driver,
                        trackName = track,
                    )
            }
        }
    }

    private fun getPerformanceByNames(driverName: String, trackName: String) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value?.copy(
                    performance = getPerformanceWO.invoke(
                        getDriverByName.invoke(driverName).id,
                        getRaceByTrack.invoke(trackName).id
                    ),
                )
        }
    }

    private fun getDriverName(idDriver: Int) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value?.copy(
                    driverName = getDriver.invoke(idDriver).name
                )
        }
    }

    private fun getTrackName(idRace: Int) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value?.copy(
                    trackName = getRace.invoke(idRace).track
                )
        }
    }

    private fun addPerformance(performance: Performance, driverName: String, trackName: String) {
        viewModelScope.launch {
            performance.idDriver = getDriverByName.invoke(driverName).id
            performance.idRace = getRaceByTrack.invoke(trackName).id
            if (addPerformance.invoke(performance)) {
                val per = getPerformanceWO.invoke(performance.idDriver, performance.idRace)
                _uiState.value = _uiState.value?.copy(
                    performance = per,
                    message = stringProvider.getString(R.string.added),
                    driverName = driverName,
                    trackName = trackName,
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
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