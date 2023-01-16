package com.moviles.f1app.ui.pantalla.user.lists.teams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.domain.usecases.teams.GetTeams
import com.moviles.f1app.ui.pantalla.admin.list.teams.EditTeamsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchTeamsViewModel @Inject constructor(
    private val getTeams: GetTeams,
) : ViewModel(){

    private val _uiState = MutableLiveData<WatchTeamsState>()
    val uiState: MutableLiveData<WatchTeamsState> get() = _uiState

    fun handleEvent(event: WatchTeamsEvent) {
        when (event) {
            WatchTeamsEvent.LoadTeams -> {
                loadTeams()
            }
        }
    }

    private fun loadTeams() {
        viewModelScope.launch {
            _uiState.value = WatchTeamsState(getTeams.invoke())
        }
    }
}