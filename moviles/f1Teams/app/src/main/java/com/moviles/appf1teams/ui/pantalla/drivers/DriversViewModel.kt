package com.moviles.appf1teams.ui.pantalla.drivers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.appf1teams.R
import com.moviles.appf1teams.domain.modelo.Driver
import com.moviles.appf1teams.domain.usecases.drivers.AddDriver
import com.moviles.appf1teams.domain.usecases.drivers.Delete
import com.moviles.appf1teams.domain.usecases.drivers.GetDrivers
import com.moviles.appf1teams.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getDrivers: GetDrivers,
    private val deleteDriver: Delete,
    private val addDriver: AddDriver,
) : ViewModel() {

    private val _uiState = MutableLiveData<DriversState>()
    val uiState: MutableLiveData<DriversState> get() = _uiState


    fun handleEvent(event: DriversEvent) {
        when (event) {
            is DriversEvent.LoadDrivers -> {
                loadDrivers(event.idTeam)
            }
            is DriversEvent.DeleteDriver -> {
                deleteDriver(event.id, event.driver)
            }
            is DriversEvent.AddDriver -> {
                addDriver(event.id, event.driver)
            }
        }
    }

    private fun loadDrivers(id: Int) {
        viewModelScope.launch {
            _uiState.value = DriversState(drivers = getDrivers.invoke(id))
        }
    }

    private fun deleteDriver(id: Int, driver: Driver) {
        viewModelScope.launch {
            if (driver.id != null) {
                deleteDriver.invoke(driver.id)
                loadDrivers(id)
            } else {
                _uiState.value = DriversState(message = stringProvider.getString(R.string.error_deleting_driver))
            }

        }
    }

    private fun addDriver(id: Int, driver: Driver) {
        viewModelScope.launch {
            if (addDriver.invoke(id, driver)) {
                _uiState.value =
                    DriversState(drivers = getDrivers.invoke(id), message = stringProvider.getString(R.string.driver_added))
            } else {
                _uiState.value =
                    _uiState.value?.copy(message = stringProvider.getString(R.string.alreadyExists))
            }

        }
    }

}