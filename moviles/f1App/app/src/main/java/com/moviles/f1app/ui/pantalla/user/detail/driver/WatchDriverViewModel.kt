package com.moviles.f1app.ui.pantalla.user.detail.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.usecases.drivers.GetDriver
import com.moviles.f1app.domain.usecases.performances.GetDriverPerformances
import com.moviles.f1app.domain.usecases.teams.GetTeam
import com.moviles.f1app.ui.pantalla.admin.detail.driver.EditDriverState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchDriverViewModel @Inject constructor(
    private val getDriver: GetDriver,
    private val getTeam: GetTeam,
    private val getDriverPerformances: GetDriverPerformances,
) : ViewModel() {

    private val _uiState = MutableLiveData<WatchDriverState>()
    val uiState: LiveData<WatchDriverState> get() = _uiState

    fun handleEvent(event: WatchDriverEvent) {
        when (event) {
            is WatchDriverEvent.GetDriver -> {
                getDriver(event.id)
            }
        }
    }

    private fun getDriver(id: Int) {
        viewModelScope.launch {
            val driver = getDriver.invoke(id)
            _uiState.value = WatchDriverState(
                driver = driver,
                teamName = getTeam.invoke(driver.idTeam).name,
                performances = getDriverPerformances.invoke(id)
            )
        }
    }

}