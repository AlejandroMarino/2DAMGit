package com.moviles.f1app.ui.pantalla.user.detail.race

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.usecases.performances.GetRacePerformances
import com.moviles.f1app.domain.usecases.races.GetRace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchRaceViewModel @Inject constructor(
    private val getRace: GetRace,
    private val getRacePerformances: GetRacePerformances,
) : ViewModel() {

    private val _uiState = MutableLiveData<WatchRaceState>()
    val uiState: MutableLiveData<WatchRaceState> get() = _uiState

    fun handleEvent(event: WatchRaceEvent) {
        when (event) {
            is WatchRaceEvent.GetRace -> {
                getRace(event.id)
            }
        }
    }

    private fun getRace(idRace: Int) {
        viewModelScope.launch {
            _uiState.value = WatchRaceState(
                race =  getRace.invoke(idRace),
                performances = getRacePerformances.invoke(idRace)
            )
        }
    }

}