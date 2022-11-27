package com.moviles.appf1teams.ui.pantalla.recycleView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.appf1teams.R
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecycleViewViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getTeams: GetTeams,
    private val delete: Delete,
    private val addTeam: AddTeam,
) : ViewModel() {

    private val _uiState = MutableLiveData<RecycleViewState>()
    val uiState: LiveData<RecycleViewState> get() = _uiState

    init {
        loadTeams()
    }

    fun handleEvent(event: RecycleViewEvent) {
        when (event) {
            RecycleViewEvent.LoadTeams -> {
                loadTeams()
            }
            is RecycleViewEvent.DeleteTeam -> {
                deleteTeam(event.team)
            }
            is RecycleViewEvent.AddTeam -> {
                addTeam(event.team)
            }
        }
    }

    private fun loadTeams() {
        viewModelScope.launch {
            _uiState.value = RecycleViewState(teams = getTeams.invoke(), error = null)
        }
    }

    //mirar si uso loadteams
    private fun deleteTeam(team: Team) {
        viewModelScope.launch {
            if (
                delete.invoke(team.id)) {
                loadTeams()
                _uiState.value = RecycleViewState(teams = getTeams.invoke())
            } else {
                _uiState.value = _uiState.value?.copy(error = stringProvider.getString(R.string.error_deleting))
            }
        }
    }

    private fun addTeam(team: Team) {
        viewModelScope.launch {
            addTeam.invoke(team)
            loadTeams()
            _uiState.value = RecycleViewState(teams = getTeams.invoke())
        }
    }
}