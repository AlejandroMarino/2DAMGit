package com.moviles.f1app.ui.pantalla.user.lists.races

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.usecases.races.GetRaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchRacesViewModel @Inject constructor(
    private val getRaces: GetRaces,
): ViewModel(){

    private val _uiState = MutableLiveData<WatchRacesState>()
    val uiState: LiveData<WatchRacesState> get() = _uiState

    fun handleEvent(event: WatchRacesEvent) {
        when (event) {
            WatchRacesEvent.LoadRaces -> {
                loadRaces()
            }
        }
    }

    private fun loadRaces() {
        viewModelScope.launch {
            _uiState.value = WatchRacesState(getRaces.invoke())
        }
    }
}