package com.moviles.appf1teams.ui.pantalla.main

import androidx.lifecycle.*
import com.moviles.appf1teams.R
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.domain.usecases.teams.Update
import com.moviles.appf1teams.utils.StringProvider
import kotlinx.coroutines.launch

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
    private val delete: Delete,
    private val update: Update,
    private val getTeams: GetTeams,
) : ViewModel() {

    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState
    private var index = 0

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.AddTeam -> {
                addTeam(event.team)
            }
            MainEvent.DeleteTeam -> {
                deleteTeam()
            }
            is MainEvent.LoadTeam -> {
                loadTeam(event.team)
            }
            is MainEvent.UpdateTeam -> {
                updateTeam(event.name, event.performance, event.tyre, event.winner)
            }
            MainEvent.NextTeam -> {
                nextTeam()
            }
            MainEvent.PreviousTeam -> {
                previousTeam()
            }
        }
    }

    private suspend fun allTeams() = getTeams.invoke()

    private fun loadTeam(team: Team) {
        viewModelScope.launch {
            _uiState.value = MainState(team = team)
            index = allTeams().indexOf(team)
        }
    }

    private fun previousTeam() {
        viewModelScope.launch {
            val teams = getTeams.invoke()
            if (teams.isNotEmpty()) {
                if (index == 0) {
                    _uiState.value = MainState(team = teams[teams.size - 1])
                    index = teams.size - 1
                } else {
                    _uiState.value = MainState(team = teams[index - 1])
                    index -= 1
                }
            } else {
                _uiState.value = MainState(message = stringProvider.getString(R.string.noMoreTeams))
            }
        }
    }

    private suspend fun getNameTeam(index: Int): String {
        val team = getTeams.invoke()[index]
        return team.name
    }

    private fun nextTeam() {
        viewModelScope.launch {
            val teams = getTeams.invoke()
            if (teams.isNotEmpty()) {
                if (index == teams.size - 1) {
                    _uiState.value = MainState(team = teams[0])
                    index = 0
                } else {
                    _uiState.value = MainState(team = teams[index + 1])
                    index += 1
                }
            } else {
                _uiState.value = MainState(message = stringProvider.getString(R.string.noMoreTeams))
            }
        }
    }

    //si añades uno con el mismo nombre peta
    private fun addTeam(team: Team) {
        viewModelScope.launch {
            if (!addTeam.invoke(team)) {
                _uiState.value = MainState(
                    message = stringProvider.getString(R.string.repeatedName),
                )
            } else {
                _uiState.value = MainState(
                    team = team, message = stringProvider.getString(R.string.teamAdded)
                )
            }
        }
    }

    private fun deleteTeam() {
        viewModelScope.launch {
            val teams = getTeams.invoke()
            if (teams.isNotEmpty()) {
                val name = getNameTeam(index)
                if (!delete.invoke(name)) {
                    _uiState.value = MainState(
                        message = stringProvider.getString(R.string.noTeamWithName),
                    )
                } else {
                    if (teams.isEmpty()) {
                        _uiState.value = MainState(
                            team = Team(),
                        )
                    } else if (index == teams.size) {
                        _uiState.value = MainState(
                            team = teams[index - 1],
                        )
                        index -= 1
                    } else {
                        _uiState.value = MainState(
                            team = teams[index],
                        )
                    }
                }
            } else {
                _uiState.value = MainState(message = stringProvider.getString(R.string.noMoreTeams))
            }
        }
    }


    //comprovar errores, si actualizas el nombre no va
    private fun updateTeam(newName: String, performance: Float, tyre: Int, winner: Boolean) {
        viewModelScope.launch {
            val name = allTeams()[index].name
            update.invoke(name, newName, performance, tyre, winner)
            _uiState.value = MainState(
                message = stringProvider.getString(R.string.updatedTeam),
            )
        }
    }
}

class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
    private val delete: Delete,
    private val update: Update,
    private val getTeams: GetTeams,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addTeam,
                delete,
                update,
                getTeams,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}