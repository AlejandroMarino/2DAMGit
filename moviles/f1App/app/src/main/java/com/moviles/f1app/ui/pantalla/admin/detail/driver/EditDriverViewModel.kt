package com.moviles.f1app.ui.pantalla.admin.detail.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.usecases.drivers.AddDriver
import com.moviles.f1app.domain.usecases.drivers.GetDriver
import com.moviles.f1app.domain.usecases.drivers.UpdateDriver
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
    private val updateDriver: UpdateDriver,
    private val getTeams: GetTeams,
    private val getTeam: GetTeam,
    private val getTeamByName: GetTeamByName,
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
            EditDriverEvent.GetTeams -> {
                getTeams()
            }
        }
    }

    private fun addDriver(driver: Driver, teamName: String) {
        viewModelScope.launch {
            driver.idTeam = getTeamByName.invoke(teamName).id
            if (addDriver.invoke(driver)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.added)
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
                    message = stringProvider.getString(R.string.updated)
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
                teams = getTeams.invoke()
            )
        }
    }

    private fun getTeams() {
        viewModelScope.launch {
            _uiState.value = EditDriverState(teams = getTeams.invoke())
        }
    }

}