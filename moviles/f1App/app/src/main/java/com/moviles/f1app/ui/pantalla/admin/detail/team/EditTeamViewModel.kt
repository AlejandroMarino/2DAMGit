package com.moviles.f1app.ui.pantalla.admin.detail.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.f1app.R
import com.moviles.f1app.domain.modelo.Team
import com.moviles.f1app.domain.usecases.teams.AddTeam
import com.moviles.f1app.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTeamViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,
) : ViewModel(){

    private val _uiState = MutableLiveData<EditTeamState>()
    val uiState: LiveData<EditTeamState> get() = _uiState

    fun handleEvent(event: EditTeamEvent) {
        when (event) {
            is EditTeamEvent.AddTeam -> {
                addTeam(event.team)
            }
        }
    }

    private fun addTeam(team: Team) {
        viewModelScope.launch {
            try {
                addTeam.invoke(team)
                _uiState.value = EditTeamState(message = stringProvider.getString(R.string.added))
            } catch (e: Exception) {
                _uiState.value = EditTeamState(message = stringProvider.getString(R.string.error_adding))
            }
        }
    }
}