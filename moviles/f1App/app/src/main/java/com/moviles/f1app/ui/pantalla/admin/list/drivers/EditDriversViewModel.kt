package com.moviles.f1app.ui.pantalla.admin.list.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.usecases.drivers.AddDriver
import com.moviles.f1app.domain.usecases.drivers.DeleteDriver
import com.moviles.f1app.domain.usecases.drivers.GetAllDrivers
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDriversViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getDrivers: GetAllDrivers,
    private val deleteDriver: DeleteDriver,
    private val addDriver: AddDriver,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditDriversState>()
    val uiState: LiveData<EditDriversState> get() = _uiState

    fun handleEvent(event: EditDriversEvent) {
        when (event) {
            EditDriversEvent.LoadDrivers -> {
                loadDrivers()
            }
            is EditDriversEvent.DeleteDriver -> {
                deleteDriver(event.driver)
            }
            is EditDriversEvent.AddDriver -> {
                addDriver(event.driver)
            }
        }
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            _uiState.value = EditDriversState(drivers = getDrivers.invoke(), error = null)
        }
    }

    private fun deleteDriver(driver: Driver) {
        viewModelScope.launch {
            if (deleteDriver.invoke(driver)) {
                loadDrivers()
                _uiState.value = EditDriversState(drivers = getDrivers.invoke())
            } else {
                _uiState.value = _uiState.value?.copy(
                    error = stringProvider.getString(R.string.error_deleting)
                )
            }
        }
    }


    private fun addDriver(driver: Driver) {
        viewModelScope.launch {
            if (addDriver.invoke(driver)) {
                loadDrivers()
                _uiState.value = EditDriversState(drivers = getDrivers.invoke())
            } else {
                _uiState.value = _uiState.value?.copy(
                    error = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }
}