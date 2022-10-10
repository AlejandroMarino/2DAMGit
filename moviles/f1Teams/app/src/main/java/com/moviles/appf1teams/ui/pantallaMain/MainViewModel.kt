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


    private fun allTeams() = getTeams()

    fun cargarTeam(index: Int) {
        _uiState.value = MainState(team = allTeams()[index])
    }

    fun previousTeam(index: Int): Int {
        val teams = getTeams()
        return if (index == 0) {
            _uiState.value = MainState(team = teams[teams.size - 1])
            teams.size - 1
        } else {
            _uiState.value = MainState(team = teams[index - 1])
            index - 1
        }
    }

    private fun getNameTeam(index: Int): String {
        val team = getTeams()[index]
        return team.name
    }

    fun nextTeam(index: Int): Int {
        val teams = getTeams()
        return if (index == teams.size - 1) {
            _uiState.value = MainState(team = teams[0])
            0
        } else {
            _uiState.value = MainState(team = teams[index + 1])
            index + 1
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

    fun deleteTeam(actualIndex: Int) : Int {
        val name = getNameTeam(actualIndex)
        if (!delete(name)) {
            _uiState.value = MainState(
                error = stringProvider.getString(R.string.noTeamWithName),
            )
            return actualIndex
        } else {
            val teams = getTeams()
            return if (actualIndex == teams.size) {
                _uiState.value = MainState(
                    team = teams[actualIndex - 1],
                )
                actualIndex - 1
            } else {
                _uiState.value = MainState(
                    team = teams[actualIndex],
                )
                actualIndex
            }
        }
    }

    fun updateTeam(index: Int, newName: String, performance: Float, tyre: Int, winner: Boolean) {
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