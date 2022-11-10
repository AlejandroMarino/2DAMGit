package com.moviles.appf1teams.ui.pantalla.recycleView

import androidx.lifecycle.*
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.utils.StringProvider
import kotlinx.coroutines.launch

class RecycleViewViewModel(
    private val stringProvider: StringProvider,
    private val getTeams: GetTeams,
    private val delete: Delete,
    private val addTeam: AddTeam,
) : ViewModel() {

    private val _uiState = MutableLiveData<RecycleViewState>()
    val uiState: LiveData<RecycleViewState> get() = _uiState

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
            _uiState.value = RecycleViewState(teams = getTeams.invoke())
        }
    }

    private fun deleteTeam(team: Team) {
        viewModelScope.launch {
            delete.invoke(team.name)
            _uiState.value = RecycleViewState(teams = getTeams.invoke())
        }
    }

    private fun addTeam(team: Team) {
        viewModelScope.launch {
            addTeam.invoke(team)
            _uiState.value = RecycleViewState(teams = getTeams.invoke())
        }
    }
}

class RecycleViewViewModelFactory(
    private val stringProvider: StringProvider,
    private val getTeams: GetTeams,
    private val delete: Delete,
    private val addTeam: AddTeam,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecycleViewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecycleViewViewModel(
                stringProvider,
                getTeams,
                delete,
                addTeam,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}