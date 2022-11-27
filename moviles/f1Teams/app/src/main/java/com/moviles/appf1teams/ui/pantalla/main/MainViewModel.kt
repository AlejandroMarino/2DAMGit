package com.moviles.appf1teams.ui.pantalla.main

import androidx.lifecycle.*
import com.moviles.appf1teams.R
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.*
import com.moviles.appf1teams.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
    private val delete: Delete,
    private val update: Update,
    private val getTeams: GetTeams,
    private val getTeamById: GetTeamById,
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
                loadTeam(event.id)
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

    private fun loadTeam(id: Int) {
        viewModelScope.launch {
            if (id == -1) {
                _uiState.value = MainState(team = Team())
                index = -1
            } else {
                val team = getTeamById.invoke(id)
                _uiState.value = MainState(team = team)
                index = allTeams().indexOf(team)
            }
        }
    }

    private fun previousTeam() {
        viewModelScope.launch {
            val teams = getTeams.invoke()
            var lim = 1
            if (index == -1) {
                lim = 0
            }
            if (teams.size > lim) {
                if (index == 0 || index == -1) {
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

    private suspend fun getIdTeam(index: Int): Int {
        val team = getTeams.invoke()[index]
        return team.id
    }

    private fun nextTeam() {
        viewModelScope.launch {
            val teams = getTeams.invoke()
            var lim = 1
            if (index == -1) {
                lim = 0
            }
            if (teams.size > lim) {
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

    private fun addTeam(team: Team) {
        viewModelScope.launch {
            if (!addTeam.invoke(team)) {
                _uiState.value = MainState(
                    message = stringProvider.getString(R.string.repeatedName),
                )
            } else {
                val t = getTeams.invoke()[allTeams().size-1]
                _uiState.value = MainState(t, message = stringProvider.getString(R.string.teamAdded))
                index = allTeams().size - 1
            }
        }
    }

    private fun deleteTeam() {
        viewModelScope.launch {
            val teams = getTeams.invoke()
            if (teams.isNotEmpty()) {
                if (index != -1) {
                    val id = getIdTeam(index)
                    if (!delete.invoke(id)) {
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
                    _uiState.value = MainState(
                        message = stringProvider.getString(R.string.noTeamWithName),
                    )
                }
            } else {
                _uiState.value = MainState(message = stringProvider.getString(R.string.noMoreTeams))
            }
        }
    }


    private fun updateTeam(name: String, performance: Float, tyre: Int, winner: Boolean) {
        viewModelScope.launch {
            val id = getIdTeam(index)
            update.invoke(id, name, performance, tyre, winner)
            _uiState.value = MainState(
                message = stringProvider.getString(R.string.updatedTeam),
            )
        }
    }
}