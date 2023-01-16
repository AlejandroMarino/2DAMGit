package com.moviles.f1app.ui.pantalla.user.lists.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.usecases.drivers.GetAllDrivers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchDriversViewModel @Inject constructor(
    private val getAllDrivers: GetAllDrivers
) : ViewModel() {

    private val _uiState = MutableLiveData<WatchDriversState>()
    val uiState: LiveData<WatchDriversState> get() = _uiState

    fun handleEvent(event: WatchDriversEvent) {
        when (event) {
            WatchDriversEvent.LoadDrivers -> {
                loadDrivers()
            }
        }
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            _uiState.value = WatchDriversState(drivers = getAllDrivers.invoke())
        }
    }
}