package com.moviles.appf1teams.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviles.appf1teams.R
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.domain.usecases.teams.Update
import com.moviles.appf1teams.utils.StringProvider

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


    private fun allTeams() = getTeams()

    fun loadTeam() {
        _uiState.value = MainState(team = allTeams()[index])
    }

    fun previousTeam() {
        val teams = getTeams()
        if (teams.isNotEmpty()) {
            if (index == 0) {
                _uiState.value = MainState(team = teams[teams.size - 1])
                index = teams.size - 1
            } else {
                _uiState.value = MainState(team = teams[index - 1])
                index -= 1
            }
        } else {
            _uiState.value = MainState(error = stringProvider.getString(R.string.noMoreTeams))
        }
    }

    private fun getNameTeam(index: Int): String {
        val team = getTeams()[index]
        return team.name
    }

    fun nextTeam() {
        val teams = getTeams()
        if (teams.isNotEmpty()) {
            if (index == teams.size - 1) {
                _uiState.value = MainState(team = teams[0])
                index = 0
            } else {
                _uiState.value = MainState(team = teams[index + 1])
                index += 1
            }
        } else {
            _uiState.value = MainState(error = stringProvider.getString(R.string.noMoreTeams))
        }
    }

    fun addTeam(name: String, performance: Float, tyre: Int, winner: Boolean) {
        val team = Team(name, performance, tyre, winner)
        if (!addTeam(team)) {
            _uiState.value = MainState(
                error = stringProvider.getString(R.string.repeatedName),
            )
        } else {
            _uiState.value = MainState(
                team = team,
            )
        }
    }

    fun deleteTeam() {
        val teams = getTeams()
        if (teams.isNotEmpty()) {
            val name = getNameTeam(index)
            if (!delete(name)) {
                _uiState.value = MainState(
                    error = stringProvider.getString(R.string.noTeamWithName),
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
        }else{
            _uiState.value = MainState(error = stringProvider.getString(R.string.noMoreTeams))
        }
    }

    fun updateTeam(newName: String, performance: Float, tyre: Int, winner: Boolean) {
        val name = allTeams()[index].name
        update(name, newName, performance, tyre, winner)
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