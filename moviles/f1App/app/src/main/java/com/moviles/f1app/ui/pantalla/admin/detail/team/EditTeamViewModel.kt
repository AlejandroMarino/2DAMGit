package com.moviles.f1app.ui.pantalla.admin.detail.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Team
import com.moviles.f1app.domain.usecases.teams.AddTeam
import com.moviles.f1app.domain.usecases.teams.GetTeam
import com.moviles.f1app.domain.usecases.teams.UpdateTeam
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTeamViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
    private val getTeam: GetTeam,
    private val updateTeam: UpdateTeam,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditTeamState>()
    val uiState: LiveData<EditTeamState> get() = _uiState

    fun handleEvent(event: EditTeamEvent) {
        when (event) {
            is EditTeamEvent.AddTeam -> {
                addTeam(event.team)
            }
            is EditTeamEvent.GetTeam -> {
                getTeam(event.id)
            }
            is EditTeamEvent.UpdateTeam -> {
                updateTeam(event.team)
            }
        }
    }

    private fun addTeam(team: Team) {
        viewModelScope.launch {
            if (addTeam.invoke(team)) {
                _uiState.value = EditTeamState(
                    getTeam.invoke(team.id),
                    stringProvider.getString(R.string.added)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_adding)
                )
            }
        }
    }

    private fun getTeam(id: Int) {
        viewModelScope.launch {
            _uiState.value = EditTeamState(team = getTeam.invoke(id))
        }
    }

    private fun updateTeam(team: Team) {
        viewModelScope.launch {
            if (updateTeam.invoke(team)) {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.updated)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error_updating)
                )
            }
        }
    }
}