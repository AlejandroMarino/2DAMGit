package com.moviles.f1app.ui.pantalla.admin.list.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Team
import com.moviles.f1app.domain.usecases.teams.AddTeam
import com.moviles.f1app.domain.usecases.teams.DeleteTeam
import com.moviles.f1app.domain.usecases.teams.GetTeams
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTeamsViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getTeams: GetTeams,
    private val deleteTeam: DeleteTeam,
    private val addTeam: AddTeam,
) : ViewModel() {

    private val _uiState = MutableLiveData<EditTeamsState>()
    val uiState: LiveData<EditTeamsState> get() = _uiState

    fun handleEvent(event: EditTeamsEvent) {
        when (event) {
            EditTeamsEvent.LoadTeams -> {
                loadTeams()
            }
            is EditTeamsEvent.DeleteTeam -> {
                deleteTeam(event.team)
            }
            is EditTeamsEvent.AddTeam -> {
                addTeam(event.team)
            }
        }
    }

    init {
        loadTeams()
    }

    private fun loadTeams() {
        viewModelScope.launch {
            _uiState.value = EditTeamsState(teams = getTeams.invoke(), error = null)
        }
    }

    private fun deleteTeam(team: Team) {
        viewModelScope.launch {
            if (
                deleteTeam.invoke(team.id)) {
                loadTeams()
                _uiState.value = EditTeamsState(teams = getTeams.invoke())
            } else {
                _uiState.value =
                    _uiState.value?.copy(error = stringProvider.getString(R.string.error_deleting))
            }
        }
    }

    private fun addTeam(team: Team) {
        viewModelScope.launch {
            if (
                addTeam.invoke(team)) {
                loadTeams()
                _uiState.value = EditTeamsState(teams = getTeams.invoke())
            } else {
                _uiState.value =
                    _uiState.value?.copy(error = stringProvider.getString(R.string.error_adding))
            }
        }
    }
}