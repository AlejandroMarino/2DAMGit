package com.moviles.appf1teams.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.domain.usecases.teams.Update
import com.moviles.appf1teams.utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
    private val delete:Delete,
    private val update: Update,
    private val getTeams: GetTeams,
) : ViewModel() {

    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    fun errorMostrado() {
//        _uiState.value = _uiState.value?.copy(error = null)
    }

    private fun allTeams() = getTeams()

    fun previousTeam(index: Int) : Int {
        val teams = getTeams()
        if (index == 0) {
            _uiState.value = MainState(team = teams[teams.size - 1])
            return teams.size - 1
        } else {
            _uiState.value = MainState(team = teams[index - 1])
            return index - 1
        }
    }

    fun nextTeam(index: Int): Int {
        val teams = getTeams()
        if (index == teams.size - 1) {
            _uiState.value = MainState(team = teams[0])
            return 0
        } else {
            _uiState.value = MainState(team = teams[index + 1])
            return index + 1
        }
    }

    fun addTeam(name: String,performance:Float,tyre:Int,winner:Boolean) {
        val team = Team(name,performance,tyre,winner)
        if (!addTeam(team)) {
            _uiState.value = MainState(
                error = "There is already a team with that name",
            )
        }else{
            _uiState.value = MainState(
                team = team,
            )
        }
    }

    fun deleteTeam(name: String) {
        if (!delete(name)) {
            _uiState.value = MainState(
                error = "There is no team with that name",
            )
        }
    }

    fun updateTeam(index: Int, newName: String, performance:Float, tyre:Int, winner:Boolean) {
        val name = allTeams()[index].name
        update(name,newName,performance,tyre,winner)
    }
}

class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
    private val delete:Delete,
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