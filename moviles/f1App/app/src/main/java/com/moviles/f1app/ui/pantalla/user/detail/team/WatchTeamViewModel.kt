package com.moviles.f1app.ui.pantalla.user.detail.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.usecases.drivers.GetDriversOfTeam
import com.moviles.f1app.domain.usecases.teams.GetTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchTeamViewModel @Inject constructor(
    private val getTeam: GetTeam,
    private val getDriversOfTeam: GetDriversOfTeam,
) : ViewModel() {

    private val _uiState = MutableLiveData<WatchTeamState>()
    val uiState: LiveData<WatchTeamState> get() = _uiState

    fun handleEvent(event: WatchTeamEvent) {
        when (event) {
            is WatchTeamEvent.GetTeam -> {
                getTeam(event.idTeam)
            }
        }
    }

    private fun getTeam(idTeam: Int) {
        viewModelScope.launch {
            _uiState.value = WatchTeamState(
                team = getTeam.invoke(idTeam),
                drivers = getDriversOfTeam.invoke(idTeam)
            )
        }
    }
}