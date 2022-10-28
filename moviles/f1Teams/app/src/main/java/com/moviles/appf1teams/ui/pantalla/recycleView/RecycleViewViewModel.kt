package com.moviles.appf1teams.ui.pantalla.recycleView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.ui.pantalla.main.MainViewModel
import com.moviles.appf1teams.utils.StringProvider

class RecycleViewViewModel(
    private val stringProvider: StringProvider,
    private val getTeams: GetTeams,
    private val delete: Delete,
) : ViewModel() {

    private val _uiState = MutableLiveData<RecycleViewState>()
    val uiState: LiveData<RecycleViewState> get() = _uiState

    fun loadTeams() {
        _uiState.value = RecycleViewState(teams = getTeams())
    }

    fun deleteTeam(team: Team) {
        delete(team.name)
        _uiState.value = RecycleViewState(teams = getTeams())
    }
}

class RecycleViewViewModelFactory(
    private val stringProvider: StringProvider,
    private val getTeams: GetTeams,
    private val delete: Delete,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecycleViewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecycleViewViewModel(
                stringProvider,
                getTeams,
                delete,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}