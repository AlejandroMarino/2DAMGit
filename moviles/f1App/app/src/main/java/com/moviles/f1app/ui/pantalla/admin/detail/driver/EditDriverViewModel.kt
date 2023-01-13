package com.moviles.f1app.ui.pantalla.admin.detail.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import com.moviles.f1app.domain.usecases.drivers.AddDriver
import com.moviles.f1app.domain.usecases.drivers.GetDriver
import com.moviles.f1app.domain.usecases.drivers.GetDriverByName
import com.moviles.f1app.domain.usecases.drivers.UpdateDriver
import com.moviles.f1app.domain.usecases.performances.AddPerformance
import com.moviles.f1app.domain.usecases.performances.DeletePerformance
import com.moviles.f1app.domain.usecases.performances.GetDriverPerformances
import com.moviles.f1app.domain.usecases.performances.GetPerformance
import com.moviles.f1app.domain.usecases.teams.GetTeam
import com.moviles.f1app.domain.usecases.teams.GetTeamByName
import com.moviles.f1app.domain.usecases.teams.GetTeams
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDriverViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addDriver: AddDriver,
    private val getDriver: GetDriver,
    private val getDriverByName: GetDriverByName,
    private val updateDriver: UpdateDriver,
    private val getTeams: GetTeams,
    private val getTeam: GetTeam,
    private val getTeamByName: GetTeamByName,
    private val deletePerformance: DeletePerformance,
    private val getPerformance: GetPerformance,
    private val getDriverPerformances: GetDriverPerformances,
    private val addPerformance: AddPerformance,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditDriverState>()
    val uiState: LiveData<EditDriverState> get() = _uiState

    fun handleEvent(event: EditDriverEvent) {
        when (event) {
            is EditDriverEvent.AddDriver -> {
                addDriver(event.driver, event.teamName)
            }
            is EditDriverEvent.UpdateDriver -> {
                updateDriver(event.driver, event.teamName)
            }
            is EditDriverEvent.GetDriver -> {
                getDriver(event.id)
            }
            is EditDriverEvent.DeletePerformance -> {
                deletePerformance(event.performance)
            }
            is EditDriverEvent.AddPerformance -> {
                addPerformance(event.performance)
            }
            EditDriverEvent.GetTeams -> {
                getTeams()
            }
        }
    }

    private fun addDriver(driver: Driver, teamName: String) {
        viewModelScope.launch {
            driver.idTeam = getTeamByName.invoke(teamName).id
            if (addDriver.invoke(driver)) {
                val dr = getDriverByName.invoke(driver.name)
                _uiState.value = _uiState.value?.copy(
                    driver = dr,
                    message = stringProvider.getString(R.string.added),
                    performances = getDriverPerformances.invoke(dr.id)
                )
            } else {
                _uiState.value =
                    _uiState.value?.copy(
                        message = stringProvider.getString(R.string.error_adding)
                    )
            }
        }
    }

    private fun updateDriver(driver: Driver, teamName: String) {
        viewModelScope.launch {
            driver.idTeam = getTeamByName.invoke(teamName).id
            if (updateDriver.invoke(driver)) {
                _uiState.value = _uiState.value?.copy(
                    driver = getDriver.invoke(driver.id),
                    message = stringProvider.getString(R.string.updated),
                    performances = getDriverPerformances.invoke(driver.id)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_updating)
                )
            }
        }
    }

    private fun getDriver(id: Int) {
        viewModelScope.launch {
            _uiState.value = EditDriverState(
                driver = getDriver.invoke(id),
                teamName = getTeam.invoke(getDriver.invoke(id).idTeam).name,
                teams = getTeams.invoke(),
                performances = getDriverPerformances.invoke(id)
            )
        }
    }

    private fun getTeams() {
        viewModelScope.launch {
            _uiState.value = EditDriverState(teams = getTeams.invoke())
        }
    }

    private fun deletePerformance(performance: PerformanceWithObjects) {
        viewModelScope.launch {
            val per: Performance = getPerformance.invoke(performance.driver.id, performance.race.id)
            if (deletePerformance.invoke(per)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.deleted),
                    driver = getDriver.invoke(performance.driver.id),
                    performances = getDriverPerformances.invoke(performance.driver.id)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_deleting)
                )
            }
        }
    }

    private fun addPerformance(performance: PerformanceWithObjects){
        viewModelScope.launch {
            val per: Performance = getPerformance.invoke(performance.driver.id, performance.race.id)
            if (addPerformance.invoke(per)) {
                _uiState.value = _uiState.value?.copy(
                    driver = getDriver.invoke(performance.driver.id),
                    performances = getDriverPerformances.invoke(performance.driver.id)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }

}